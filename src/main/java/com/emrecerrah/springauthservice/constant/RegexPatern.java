package com.emrecerrah.springauthservice.constant;

public class RegexPatern {
    public static final String REGEX_PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    public static final String REGEX_NAME_PATTERN = "\\A(?!\\s*\\Z).+";

}
