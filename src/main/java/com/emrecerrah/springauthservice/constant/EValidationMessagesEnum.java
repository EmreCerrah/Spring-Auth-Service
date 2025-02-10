package com.emrecerrah.springauthservice.constant;

public class EValidationMessagesEnum {
    public static final int MIN_SIZE_LIMIT_NAME = 4;
    public static final int MIN_SIZE_LIMIT_PASS = 8;
    public static final int MAX_SIZE_LIMIT_NAME = 40;
    public static final int MAX_SIZE_LIMIT_PASS = 80;

    public static final String ENTER_USERNAME_NOT_NULL="Please enter your username";
    public static final String ENTER_PASSWORD_NOT_NULL="Please enter valid password";
    public static final String ENTER_EMAIL_NOT_NULL="Please enter your email";
    public static final String INVALID_USERNAME ="Your username must consist of the characters.";
    public static final String INVALID_PASSWORD ="Please enter valid password!";
    public static final String MIN_SIZE_USERNAME ="Your username should be at least "+MIN_SIZE_LIMIT_NAME+" chars";
    public static final String MIN_SIZE_PASSWORD ="Your password should be at least "+MIN_SIZE_LIMIT_PASS+" chars";

}
