package com.emrecerrah.springauthservice.execption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessage(ErrorType.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(AuthServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAuthException(AuthServiceException ex) {
        return new ResponseEntity(createErrorMessage(ex),ex.getType().getHttpStatus());
    }

    private ErrorMessage createErrorMessage(AuthServiceException ex) {
        return new ErrorMessage(ex.getType());
    }
}