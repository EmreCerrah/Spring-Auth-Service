package com.emrecerrah.springauthservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginRequestDTO {
    private String username;
    private String password;
}
