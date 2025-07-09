package com.emrecerrah.springauthservice.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.emrecerrah.springauthservice.constant.EValidationMessagesEnum.*;
import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_PASSWORD_PATTERN;

/**
 * Data Transfer Object for password change requests.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequestDTO {
    
    @NotBlank(message = ENTER_PASSWORD_NOT_NULL)
    private String currentPassword;
    
    @NotBlank(message = ENTER_PASSWORD_NOT_NULL)
    @Size(min = MIN_SIZE_LIMIT_PASS, max = MAX_SIZE_LIMIT_PASS, message = MIN_SIZE_PASSWORD)
    @Pattern(regexp = REGEX_PASSWORD_PATTERN, message = INVALID_PASSWORD)
    private String newPassword;
    
    @NotBlank(message = ENTER_PASSWORD_NOT_NULL)
    private String confirmNewPassword;
}