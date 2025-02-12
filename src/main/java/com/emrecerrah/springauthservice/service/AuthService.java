package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.constant.ERole;
import com.emrecerrah.springauthservice.execption.AuthServiceException;
import com.emrecerrah.springauthservice.execption.ErrorMessage;
import com.emrecerrah.springauthservice.execption.ErrorType;
import com.emrecerrah.springauthservice.mapper.IAuthMapper;
import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.MessageResponse;
import com.emrecerrah.springauthservice.repository.IAuthRepository;
import com.emrecerrah.springauthservice.repository.IRoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository repository;

//    private final JwtTokenManager jwtTokenManager;
    private final IRoleRepository roleRepository;

    public AuthService(IAuthRepository repository, IRoleRepository roleRepository) {
        super(repository);
        this.repository = repository;
        this.roleRepository = roleRepository;
//        this.jwtTokenManager = jwtTokenManager;
    }
//FIXME: AuthServiceException mi MessageResponse kullanilacak karar ver ifleri duzenle
    public ResponseEntity<RegisterResponseDTO> doRegister(RegisterRequestDTO requestDto) {
        if (!requestDto.getPassword().equals(requestDto.getRePassword())) {
            throw new AuthServiceException(ErrorType.REGISTER_PASSWORD_MISMATCH);
        }

        if (IAuthRepository.existsByUsername(requestDto.getUsername())) {
            throw new AuthServiceException(ErrorType.REGISTER_USERNAME_EXISTS));
        }

        if (IAuthRepository.existsByEmail(requestDto.getEmail())) {
            throw new AuthServiceException(ErrorType.REGISTER_EMAIL_EXISTS));
        }

        Auth auth = IAuthMapper.INSTANCE.toAuth(requestDto);

        auth.addRole(roleRepository.findByName(ERole.ROLE_USER).get());
        save(auth);

        RegisterResponseDto responseDto = IAuthMapper.INSTANCE.authToRegisterDto(auth);

        return ResponseEntity.ok(responseDto);
    }



    public String doLogin(DoLoginRequestDto dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }




}
