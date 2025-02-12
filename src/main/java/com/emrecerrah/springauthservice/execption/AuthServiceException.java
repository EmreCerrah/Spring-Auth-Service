package com.emrecerrah.springauthservice.execption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthServiceException extends RuntimeException {
    private final ErrorType type;
}