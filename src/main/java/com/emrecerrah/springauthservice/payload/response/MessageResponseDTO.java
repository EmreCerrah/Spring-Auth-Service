package com.emrecerrah.springauthservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic response DTO for operations that return a simple message.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {
    
    private String message;
    private boolean success;
    
    /**
     * Creates a successful response with the given message.
     * 
     * @param message The success message
     * @return A MessageResponseDTO with success=true
     */
    public static MessageResponseDTO success(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .success(true)
                .build();
    }
    
    /**
     * Creates a failure response with the given message.
     * 
     * @param message The error message
     * @return A MessageResponseDTO with success=false
     */
    public static MessageResponseDTO error(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .success(false)
                .build();
    }
}