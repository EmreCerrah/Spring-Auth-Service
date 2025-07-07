package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.mapper.IAuthMapper;
import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.repository.IAuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IAuthRepository repository;

    public CustomUserDetailsService(IAuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return IAuthMapper.INSTANCE.toUserDetails(auth);
    }
}
