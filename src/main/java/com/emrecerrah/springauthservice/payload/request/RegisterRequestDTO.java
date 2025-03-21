package com.emrecerrah.springauthservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;

    private String email;

    private String password;

    private String rePassword;
}
