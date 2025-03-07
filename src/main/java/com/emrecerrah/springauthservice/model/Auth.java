package com.emrecerrah.springauthservice.model;

import com.emrecerrah.springauthservice.constant.EValidationMessagesEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_NAME_PATTERN;
import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_PASSWORD_PATTERN;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "auth") // MongoDB koleksiyon adı
public class Auth extends BaseEntity {

    @Id
    private String id; // MongoDB'de ObjectId String olarak saklanır

    @Size(min = EValidationMessagesEnum.MIN_SIZE_LIMIT_NAME,
            max = EValidationMessagesEnum.MAX_SIZE_LIMIT_NAME,
            message = EValidationMessagesEnum.MIN_SIZE_USERNAME)
    @NotNull(message = EValidationMessagesEnum.ENTER_USERNAME_NOT_NULL)
    @Pattern(regexp = REGEX_NAME_PATTERN, message = EValidationMessagesEnum.INVALID_USERNAME)
    private String username;

    @Email
    @NotNull(message = EValidationMessagesEnum.ENTER_EMAIL_NOT_NULL)
    private String email;

    @NotNull(message = EValidationMessagesEnum.ENTER_PASSWORD_NOT_NULL)
    @Size(min = EValidationMessagesEnum.MIN_SIZE_LIMIT_PASS,
            max = EValidationMessagesEnum.MAX_SIZE_LIMIT_PASS,
            message = EValidationMessagesEnum.MIN_SIZE_PASSWORD)
    @Pattern(regexp = REGEX_PASSWORD_PATTERN, message = EValidationMessagesEnum.INVALID_PASSWORD)
    private String password;

    @DBRef // MongoDB'de ilişkili koleksiyon için kullanılır
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public boolean addRole(Role role) {
        return roles.add(role);
    }
}



