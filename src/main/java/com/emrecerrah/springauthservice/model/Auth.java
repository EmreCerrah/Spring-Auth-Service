package com.emrecerrah.springauthservice.model;

import com.emrecerrah.springauthservice.constant.EValidationMessagesEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_NAME_PATTERN;
import static com.emrecerrah.springauthservice.constant.RegexPatern.REGEX_PASSWORD_PATTERN;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder // bir siniftan nesne türetmek için kullanilir
@Data // set ve get metodlarini otomatik tanimlar
@NoArgsConstructor // bos paramtereli hazırlayıcı yapıcı metodu oluşturur.
@AllArgsConstructor // dolu paramtereli hazırlayıcı yapıcı metodu oluşturur.
@ToString // nesne bilgisini terminale yazdirmak icindir
@Entity
@Table(name = "auth")
public class Auth extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Size(min = EValidationMessagesEnum.MIN_SIZE_LIMIT_NAME, max = EValidationMessagesEnum.MAX_SIZE_LIMIT_NAME , message = EValidationMessagesEnum.MIN_SIZE_USERNAME)
    @Column(unique = true, nullable = false)
    @NotNull(message = EValidationMessagesEnum.ENTER_USERNAME_NOT_NULL)
    @Pattern(regexp = REGEX_NAME_PATTERN,  message = EValidationMessagesEnum.INVALID_USERNAME)
    private String username;

    @Email
    @Column(unique = true)
    @NotNull(message =EValidationMessagesEnum.ENTER_EMAIL_NOT_NULL)
    private String email;

    @NotNull(message = EValidationMessagesEnum.ENTER_PASSWORD_NOT_NULL)
    @Size(min = EValidationMessagesEnum.MIN_SIZE_LIMIT_PASS, max = EValidationMessagesEnum.MAX_SIZE_LIMIT_PASS , message = EValidationMessagesEnum.MIN_SIZE_PASSWORD)
    @Pattern(regexp = REGEX_PASSWORD_PATTERN ,message = EValidationMessagesEnum.INVALID_PASSWORD)
    private String password;

}
