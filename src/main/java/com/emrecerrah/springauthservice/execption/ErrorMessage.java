package com.emrecerrah.springauthservice.execption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorMessage {

    private ErrorType errorType;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    public ErrorMessage(ErrorType errorType){
        this.errorType=errorType;
    }
}