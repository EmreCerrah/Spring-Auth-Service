package com.emrecerrah.springauthservice.config;

import com.emrecerrah.springauthservice.constant.ERole;
import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.repository.IAuthRepository;
import com.emrecerrah.springauthservice.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceInitializer implements CommandLineRunner {

    private final IAuthRepository authRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${AuthService.admin.username}")
    private String adminUsername;
    @Value("${AuthService.admin.password}")
    private String adminPassword;
    @Value("${AuthService.admin.email}")
    private String adminEmail;

    public AuthServiceInitializer(IAuthRepository authRepository,
                                  IRoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!authRepository.existsByUsername(adminUsername)) {
            Auth admin = Auth.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .build();
            admin.addRole(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow());
            authRepository.save(admin);
            System.out.println("Admin user created: " + adminUsername);
        } else {
            System.out.println("Admin user already exists: " + adminUsername);
        }
    }
}
