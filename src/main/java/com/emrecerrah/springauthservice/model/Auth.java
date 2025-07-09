package com.emrecerrah.springauthservice.model;

import com.emrecerrah.springauthservice.constant.EValidationMessagesEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_NAME_PATTERN;
import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_PASSWORD_PATTERN;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "auth") // PostgreSQL'de tablo adı
public class Auth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = EValidationMessagesEnum.MIN_SIZE_LIMIT_NAME,
            max = EValidationMessagesEnum.MAX_SIZE_LIMIT_NAME,
            message = EValidationMessagesEnum.MIN_SIZE_USERNAME)
    @NotNull(message = EValidationMessagesEnum.ENTER_USERNAME_NOT_NULL)
    @Pattern(regexp = REGEX_NAME_PATTERN, message = EValidationMessagesEnum.INVALID_USERNAME)
    @Column(nullable = false, unique = true)
    private String username;

    @Email
    @NotNull(message = EValidationMessagesEnum.ENTER_EMAIL_NOT_NULL)
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = EValidationMessagesEnum.ENTER_PASSWORD_NOT_NULL)
    @Size(min = EValidationMessagesEnum.MIN_SIZE_LIMIT_PASS,
            max = EValidationMessagesEnum.MAX_SIZE_LIMIT_PASS,
            message = EValidationMessagesEnum.MIN_SIZE_PASSWORD)
    @Pattern(regexp = REGEX_PASSWORD_PATTERN, message = EValidationMessagesEnum.INVALID_PASSWORD)
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auth_roles",
            joinColumns = @JoinColumn(name = "auth_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public boolean addRole(Role role) {
        return roles.add(role);
    }
}



