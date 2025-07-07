package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.mapper.IAuthMapper;
import com.emrecerrah.springauthservice.model.UserDetailsImpl;
import com.emrecerrah.springauthservice.payload.request.LoginRequestDTO;
import com.emrecerrah.springauthservice.payload.response.LoginResponseDTO;
import com.emrecerrah.springauthservice.util.JwtTokenManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final DaoAuthenticationProvider authenticationProvider;
    private final JwtTokenManager jwtTokenManager;

    public LoginService(DaoAuthenticationProvider authenticationProvider, JwtTokenManager jwtTokenManager) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenManager = jwtTokenManager;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenManager.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        LoginResponseDTO response = IAuthMapper.INSTANCE.toLoginResponseDTO(userDetails);
        response.setToken(jwt);
        return response;
    }
}
