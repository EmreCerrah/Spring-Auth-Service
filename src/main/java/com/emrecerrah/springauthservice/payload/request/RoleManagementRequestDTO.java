package com.emrecerrah.springauthservice.payload.request;

import com.emrecerrah.springauthservice.constant.ERole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for role management requests.
 * Used for granting or revoking roles to/from users.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleManagementRequestDTO {
    
    @NotBlank(message = "Username cannot be blank")
    private String username;
    
    @NotNull(message = "Role cannot be null")
    private ERole role;
    
    /**
     * Indicates whether to grant or revoke the role.
     * true = grant, false = revoke
     */
    @NotNull(message = "Operation type cannot be null")
    private Boolean grant;
}