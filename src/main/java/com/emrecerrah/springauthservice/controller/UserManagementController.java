package com.emrecerrah.springauthservice.controller;

import com.emrecerrah.springauthservice.payload.request.PasswordChangeRequestDTO;
import com.emrecerrah.springauthservice.payload.request.RoleManagementRequestDTO;
import com.emrecerrah.springauthservice.payload.request.UsernameChangeRequestDTO;
import com.emrecerrah.springauthservice.payload.response.MessageResponseDTO;
import com.emrecerrah.springauthservice.service.UserManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.emrecerrah.springauthservice.constant.EndPoint.*;

/**
 * Controller for user management operations.
 * Provides endpoints for changing passwords, usernames, and managing roles.
 */
@CrossOrigin
@RestController
@RequestMapping(ENDPOINT_USER)
@RequiredArgsConstructor
public class UserManagementController {
    
    private final UserManagementService userManagementService;
    
    /**
     * Changes the password for the currently authenticated user.
     * 
     * @param request The password change request
     * @return ResponseEntity containing a success or error message
     */
    @PostMapping(ENDPOINT_CHANGE_PASSWORD)
    public ResponseEntity<MessageResponseDTO> changePassword(@Valid @RequestBody PasswordChangeRequestDTO request) {
        return ResponseEntity.ok(userManagementService.changePassword(request));
    }
    
    /**
     * Changes the username for the currently authenticated user.
     * 
     * @param request The username change request
     * @return ResponseEntity containing a success or error message
     */
    @PostMapping(ENDPOINT_CHANGE_USERNAME)
    public ResponseEntity<MessageResponseDTO> changeUsername(@Valid @RequestBody UsernameChangeRequestDTO request) {
        return ResponseEntity.ok(userManagementService.changeUsername(request));
    }
    
    /**
     * Manages roles for a user (grant or revoke).
     * Only administrators can perform this operation.
     * 
     * @param request The role management request
     * @return ResponseEntity containing a success or error message
     */
    @PostMapping(ENDPOINT_MANAGE_ROLE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponseDTO> manageRole(@Valid @RequestBody RoleManagementRequestDTO request) {
        return ResponseEntity.ok(userManagementService.manageRole(request));
    }

    @PostMapping(ENDPOINT_DELETE_USER)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponseDTO> deleteUser(@Valid @RequestBody String userId) {
        return ResponseEntity.ok(userManagementService.deleteUser(UUID.fromString(userId)));
    }
}