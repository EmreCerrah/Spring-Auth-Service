package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.constant.ERole;
import com.emrecerrah.springauthservice.execption.AuthServiceException;
import com.emrecerrah.springauthservice.execption.ErrorType;
import com.emrecerrah.springauthservice.mapper.IAuthMapper;
import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.model.UserDetailsImpl;
import com.emrecerrah.springauthservice.payload.request.LoginRequestDTO;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.LoginResponseDTO;
import com.emrecerrah.springauthservice.payload.response.RegisterResponseDTO;
import com.emrecerrah.springauthservice.repository.IAuthRepository;
import com.emrecerrah.springauthservice.repository.IRoleRepository;
import com.emrecerrah.springauthservice.util.JwtTokenManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class
AuthService extends ServiceManager<Auth, String> implements UserDetailsService {

    private final IAuthRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final IRoleRepository roleRepository;
    private final DaoAuthenticationProvider authenticationProvider; ///TODO: authenticationManager kullanilirsa bunu kaldir

    public AuthService(IAuthRepository repository, JwtTokenManager jwtTokenManager, IRoleRepository roleRepository, DaoAuthenticationProvider authenticationProvider) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.roleRepository = roleRepository;
        this.authenticationProvider = authenticationProvider;
    }

    //FIXME: AuthServiceException mi MessageResponse kullanilacak karar ver ifleri duzenle
    public RegisterResponseDTO doRegister(RegisterRequestDTO requestDto) {
        if (!requestDto.getPassword().equals(requestDto.getRePassword())) {
            throw new AuthServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);
        }

        if (repository.existsByUsername(requestDto.getUsername())) {
            throw new AuthServiceException(ErrorType.REGISTER_USERNAME_EXISTS);
        }

        if (repository.existsByEmail(requestDto.getEmail())) {
            throw new AuthServiceException(ErrorType.REGISTER_EMAIL_EXISTS);
        }

        Auth auth = IAuthMapper.INSTANCE.toAuth(requestDto);
        auth.addRole(roleRepository.findByName(ERole.ROLE_USER).get());
        save(auth);

        return IAuthMapper.INSTANCE.authToResponseDto(auth);
    }


    public LoginResponseDTO doLogin(LoginRequestDTO dto) {

        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenManager.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        LoginResponseDTO response = IAuthMapper.INSTANCE.toLoginResponseDTO(userDetails);
        response.setToken(jwt);
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return IAuthMapper.INSTANCE.toUserDetails(auth);
    }
}

