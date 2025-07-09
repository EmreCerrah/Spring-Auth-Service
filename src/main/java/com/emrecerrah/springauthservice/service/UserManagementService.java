package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.constant.ERole;
import com.emrecerrah.springauthservice.execption.AuthServiceException;
import com.emrecerrah.springauthservice.execption.ErrorType;
import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.model.Role;
import com.emrecerrah.springauthservice.payload.request.PasswordChangeRequestDTO;
import com.emrecerrah.springauthservice.payload.request.RoleManagementRequestDTO;
import com.emrecerrah.springauthservice.payload.request.UsernameChangeRequestDTO;
import com.emrecerrah.springauthservice.payload.response.MessageResponseDTO;
import com.emrecerrah.springauthservice.repository.IAuthRepository;
import com.emrecerrah.springauthservice.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing user accounts.
 * Provides functionality for changing passwords, usernames, and managing roles.
 */
@Service
@RequiredArgsConstructor
public class UserManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    private final IAuthRepository authRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Changes the password for the currently authenticated user.
     * 
     * @param request The password change request
     * @return A message indicating success or failure
     */
    @Transactional
    public MessageResponseDTO changePassword(PasswordChangeRequestDTO request) {
        // Get current user
        Auth currentUser = getCurrentUser();

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
            logger.warn("Password change attempt with incorrect current password for user: {}", currentUser.getUsername());
            throw new AuthServiceException(ErrorType.DOLOGIN_USERNAME_OR_PASSWORD_MISMATCH);
        }

        // Verify new password and confirmation match
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            logger.warn("Password change attempt with mismatched new passwords for user: {}", currentUser.getUsername());
            throw new AuthServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);
        }

        // Update password
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        updateUserPassword(currentUser, encodedPassword);

        logger.info("Password changed successfully for user: {}", currentUser.getUsername());
        return MessageResponseDTO.success("Password changed successfully");
    }

    /**
     * Changes the username for the currently authenticated user.
     * 
     * @param request The username change request
     * @return A message indicating success or failure
     */
    @Transactional
    public MessageResponseDTO changeUsername(UsernameChangeRequestDTO request) {
        // Get current user
        Auth currentUser = getCurrentUser();

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), currentUser.getPassword())) {
            logger.warn("Username change attempt with incorrect password for user: {}", currentUser.getUsername());
            throw new AuthServiceException(ErrorType.DOLOGIN_USERNAME_OR_PASSWORD_MISMATCH);
        }

        // Check if new username is already taken
        if (authRepository.existsByUsername(request.getNewUsername())) {
            logger.warn("Username change attempt with existing username: {}", request.getNewUsername());
            throw new AuthServiceException(ErrorType.REGISTER_USERNAME_EXISTS);
        }

        // Update username
        String oldUsername = currentUser.getUsername();
        updateUsername(currentUser, request.getNewUsername());

        logger.info("Username changed from {} to {} successfully", oldUsername, request.getNewUsername());
        return MessageResponseDTO.success("Username changed successfully");
    }

    /**
     * Manages roles for a user (grant or revoke).
     * Only administrators can perform this operation.
     * 
     * @param request The role management request
     * @return A message indicating success or failure
     */
    @Transactional
    public MessageResponseDTO manageRole(RoleManagementRequestDTO request) {
        // Verify current user is an admin
        Auth currentUser = getCurrentUser();
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName() == ERole.ROLE_ADMIN);

        if (!isAdmin) {
            logger.warn("Role management attempt by non-admin user: {}", currentUser.getUsername());
            throw new AuthServiceException(ErrorType.INVALID_PARAMETER);
        }

        // Find target user
        Auth targetUser = authRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.warn("Role management attempt for non-existent user: {}", request.getUsername());
                    return new AuthServiceException(ErrorType.USERNAME_NOT_EXISTS);
                });

        // Find role
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> {
                    logger.error("Role {} not found in database", request.getRole());
                    return new AuthServiceException(ErrorType.INTERNAL_SERVER_ERROR);
                });

        // Grant or revoke role
        String operation = request.getGrant() ? "granted" : "revoked";

        if (request.getGrant()) {
            // Add role if not already present
            if (targetUser.addRole(role)) {
                authRepository.save(targetUser);
                logger.info("Role {} granted to user {}", request.getRole(), request.getUsername());
                return MessageResponseDTO.success("Role " + request.getRole() + " " + operation + " successfully");
            } else {
                logger.info("User {} already has role {}", request.getUsername(), request.getRole());
                return MessageResponseDTO.success("User already has this role");
            }
        } else {
            // Remove role if present
            if (targetUser.getRoles().removeIf(r -> r.getName() == request.getRole())) {
                authRepository.save(targetUser);
                logger.info("Role {} revoked from user {}", request.getRole(), request.getUsername());
                return MessageResponseDTO.success("Role " + request.getRole() + " " + operation + " successfully");
            } else {
                logger.info("User {} doesn't have role {}", request.getUsername(), request.getRole());
                return MessageResponseDTO.success("User doesn't have this role");
            }
        }
    }
    @Transactional
    public MessageResponseDTO deleteUser(UUID userId) {
        try {
            authRepository.deleteById(userId);
        } catch (Exception e) {
            logger.error("Error deleting user for user id {}: {}", userId, e.getMessage());
            throw new AuthServiceException(ErrorType.INTERNAL_SERVER_ERROR);
        }
        return MessageResponseDTO.success("User deleted successfully");
    }

    /**
     * Gets the currently authenticated user.
     * 
     * @return The authenticated user entity
     * @throws AuthServiceException if no user is authenticated or user not found
     */
    private Auth getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("Attempt to access user management functionality without authentication");
            throw new AuthServiceException(ErrorType.INVALID_PARAMETER);
        }

        String username = authentication.getName();
        return authRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("Authenticated user {} not found in database", username);
                    return new AuthServiceException(ErrorType.USERNAME_NOT_EXISTS);
                });
    }

    /**
     * Updates a user's password.
     * 
     * @param user The user to update
     * @param encodedPassword The new encoded password
     */
    private void updateUserPassword(Auth user, String encodedPassword) {
        try {
            user.setPassword(passwordEncoder.encode(encodedPassword));
            authRepository.save(user);
        } catch (Exception e) {
            logger.error("Error updating password for user {}: {}", user.getUsername(), e.getMessage());
            throw new AuthServiceException(ErrorType.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a user's username.
     * 
     * @param user The user to update
     * @param newUsername The new username
     */
    private void updateUsername(Auth user, String newUsername) {
        try {
            user.setUsername(newUsername);
            authRepository.save(user);
        } catch (Exception e) {
            logger.error("Error updating username for user {}: {}", user.getUsername(), e.getMessage());
            throw new AuthServiceException(ErrorType.INTERNAL_SERVER_ERROR);
        }
    }
}
