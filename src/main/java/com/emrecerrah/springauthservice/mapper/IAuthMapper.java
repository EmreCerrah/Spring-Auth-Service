package com.emrecerrah.springauthservice.mapper;

import com.emrecerrah.springauthservice.model.Auth;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    IAuthMapper INSTANCE = Mappers.getMapper( IAuthMapper.class );


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

    Auth toAuth(final LoginRequestDto dto);

    RegisterRequestDTO authToRegisterDto(final Auth auth);
    LoginResponseDto authToLoginDto(final Auth auth);
}

//    Auth dtoToAuth(final Dto dto);
//    DTO ----> Auth
//    Auth ---> DTO


