package com.emrecerrah.springauthservice.execption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex) {
        logger.error("Unhandled exception occurred: ", ex);
        return new ResponseEntity<>(new ErrorMessage(ErrorType.INTERNAL_SERVER_ERROR), 
                                   ErrorType.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AuthServiceException ex) {
        logger.warn("Auth service exception: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage(ex.getType()), ex.getType().getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.warn("Validation error: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
