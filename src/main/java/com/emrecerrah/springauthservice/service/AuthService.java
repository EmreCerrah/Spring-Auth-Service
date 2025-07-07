package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.payload.request.LoginRequestDTO;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.LoginResponseDTO;
import com.emrecerrah.springauthservice.payload.response.RegisterResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Service for handling authentication operations.
 * Acts as a facade for specialized registration and login services.
 */
@Service
public class AuthService {
    private final RegisterService registerService;
    private final LoginService loginService;

    public AuthService(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    /**
     * Registers a new user in the system.
     * 
     * @param dto The registration request containing user details
     * @return RegisterResponseDTO containing the registered user information
     */
    public RegisterResponseDTO doRegister(RegisterRequestDTO dto) {
        return registerService.register(dto);
    }

    /**
     * Authenticates a user and provides a JWT token.
     * 
     * @param dto The login request containing credentials
     * @return LoginResponseDTO containing the JWT token and user information
     */
    public LoginResponseDTO doLogin(LoginRequestDTO dto) {
        return loginService.login(dto);
    }
}
