package com.emrecerrah.springauthservice.execption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {

    REGISTER_PASSWORD_MISMATCH (1004, "The entered passwords did not match.", HttpStatus.BAD_REQUEST),
    REGISTER_USERNAME_EXISTS (1005, "Username is already taken!", HttpStatus.BAD_REQUEST),
    REGISTER_EMAIL_EXISTS (1005, "Email is already in use!", HttpStatus.BAD_REQUEST),

    USERNAME_NOT_EXISTS (2000, "The username you are looking for is not registered in the system", HttpStatus.NOT_FOUND),
    INVALID_TOKEN (2001, "invalid token.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER (2002, "You entered invalid parameters.", HttpStatus.UNAUTHORIZED),

    DOLOGIN_USERNAME_OR_PASSWORD_MISMATCH  (3000, "User or password name is incorrect.", HttpStatus.BAD_REQUEST),

    BAD_REQUEST (6000, "You made an invalid request", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR (7000, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE (7500, "Service Unavailable.", HttpStatus.SERVICE_UNAVAILABLE);

    private int code;
    private String message;
    private HttpStatus httpStatus;

}