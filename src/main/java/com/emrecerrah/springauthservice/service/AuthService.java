package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.payload.request.LoginRequestDTO;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.LoginResponseDTO;
import com.emrecerrah.springauthservice.payload.response.RegisterResponseDTO;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final RegisterService registerService;
    private final LoginService loginService;

    public AuthService(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    public RegisterResponseDTO doRegister(RegisterRequestDTO dto) {
        return registerService.register(dto);
    }

    public LoginResponseDTO doLogin(LoginRequestDTO dto) {
        return loginService.login(dto);
    }
}
