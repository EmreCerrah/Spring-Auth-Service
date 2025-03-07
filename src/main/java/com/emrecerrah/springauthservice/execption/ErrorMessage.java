package com.emrecerrah.springauthservice.execption;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private ErrorType errorType;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorMessage(ErrorType errorType){
        this.errorType=errorType;
    }
}