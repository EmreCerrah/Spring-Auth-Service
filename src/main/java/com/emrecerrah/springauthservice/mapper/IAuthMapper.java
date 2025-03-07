package com.emrecerrah.springauthservice.mapper;

import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.model.UserDetailsImpl;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.LoginResponseDTO;
import com.emrecerrah.springauthservice.payload.response.RegisterResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface IAuthMapper {
    /*
       @Mappings({
               @Mapping(target = "authId", source = "id"),
               @Mapping(target = "username", source = "uname"),
               @Mapping(target = "email", source = "eposta")
       })
       @Mapping(target = "authId", source = "id")
       */
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);


    @Mapping(target = "password", expression = "java(encodePassword(dto.getPassword()))")
//    @Mapping(target = "createdAt", expression = "java(getCurrentTimestamp())")
//    @Mapping(target = "state", constant = "true")
    Auth toAuth(RegisterRequestDTO dto);

    default String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

//    default long getCurrentTimestamp() {
//        return System.currentTimeMillis();
//    }

    RegisterResponseDTO authToResponseDto(final Auth auth);



    @Mapping(target = "authorities", expression = "java(mapRoles(user))")
    UserDetailsImpl toUserDetails(Auth auth);

    default List<SimpleGrantedAuthority> mapRoles(Auth user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Mapping(target = "roles", expression = "java(mapAuthorities(userDetails))")
//    @Mapping(target = "type", constant = "Bearer")
    LoginResponseDTO toLoginResponseDTO(UserDetailsImpl userDetails);

    default List<String> mapAuthorities(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}

//    Auth dtoToAuth(final Dto dto);
//    DTO ----> Auth
//    Auth ---> DTO


