package com.emrecerrah.springauthservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private List<String> roles;
}
