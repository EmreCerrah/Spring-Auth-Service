package com.emrecerrah.springauthservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
public class RegisterResponseDTO {

    private Long id;
    private String username;
    private String email;
}