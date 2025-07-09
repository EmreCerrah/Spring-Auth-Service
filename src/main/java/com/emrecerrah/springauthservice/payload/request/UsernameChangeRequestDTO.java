package com.emrecerrah.springauthservice.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.emrecerrah.springauthservice.constant.EValidationMessagesEnum.*;
import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_NAME_PATTERN;

/**
 * Data Transfer Object for username change requests.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameChangeRequestDTO {
    
    @NotBlank(message = ENTER_PASSWORD_NOT_NULL)
    private String password;
    
    @NotBlank(message = ENTER_USERNAME_NOT_NULL)
    @Size(min = MIN_SIZE_LIMIT_NAME, max = MAX_SIZE_LIMIT_NAME, message = MIN_SIZE_USERNAME)
    @Pattern(regexp = REGEX_NAME_PATTERN, message = INVALID_USERNAME)
    private String newUsername;
}