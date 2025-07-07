package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.constant.ERole;
import com.emrecerrah.springauthservice.execption.AuthServiceException;
import com.emrecerrah.springauthservice.execption.ErrorType;
import com.emrecerrah.springauthservice.mapper.IAuthMapper;
import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.model.Role;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.RegisterResponseDTO;
import com.emrecerrah.springauthservice.repository.IAuthRepository;
import com.emrecerrah.springauthservice.repository.IRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    private final IAuthRepository repository;
    private final IRoleRepository roleRepository;

    public RegisterService(IAuthRepository repository, IRoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO requestDto) {
        // Validate registration data
        validateRegistrationRequest(requestDto);

        // Create auth entity using mapper (which handles password encoding)
        Auth auth = IAuthMapper.INSTANCE.toAuth(requestDto);

        // Assign user role
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> {
                logger.error("Role ROLE_USER not found in database");
                return new AuthServiceException(ErrorType.INTERNAL_SERVER_ERROR);
            });
        auth.addRole(userRole);

        // Save user and return response
        repository.save(auth);
        logger.info("New user registered: {}", auth.getUsername());

        return IAuthMapper.INSTANCE.authToResponseDto(auth);
    }

    private void validateRegistrationRequest(RegisterRequestDTO requestDto) {
        // Check if passwords match
        if (!requestDto.getPassword().equals(requestDto.getRePassword())) {
            logger.warn("Password mismatch during registration attempt for username: {}", requestDto.getUsername());
            throw new AuthServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);
        }

        // Check if username is already taken
        if (repository.existsByUsername(requestDto.getUsername())) {
            logger.warn("Registration attempt with existing username: {}", requestDto.getUsername());
            throw new AuthServiceException(ErrorType.REGISTER_USERNAME_EXISTS);
        }

        // Check if email is already in use
        if (repository.existsByEmail(requestDto.getEmail())) {
            logger.warn("Registration attempt with existing email: {}", requestDto.getEmail());
            throw new AuthServiceException(ErrorType.REGISTER_EMAIL_EXISTS);
        }
    }
}
