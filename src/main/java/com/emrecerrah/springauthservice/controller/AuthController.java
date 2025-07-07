package com.emrecerrah.springauthservice.controller;

import com.emrecerrah.springauthservice.payload.request.LoginRequestDTO;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.LoginResponseDTO;
import com.emrecerrah.springauthservice.payload.response.RegisterResponseDTO;
import com.emrecerrah.springauthservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emrecerrah.springauthservice.constant.EndPoint.*;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(ENDPOINT_AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping(ENDPOINT_REGISTER)
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.doRegister(dto));
    }

    @PostMapping(ENDPOINT_LOGIN)
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.doLogin(dto));
    }
}
