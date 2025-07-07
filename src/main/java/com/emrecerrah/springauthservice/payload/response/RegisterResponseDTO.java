package com.emrecerrah.springauthservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class RegisterResponseDTO {

    private UUID id;
    private String username;
    private String email;
}