package ru.spring.shop.exeption;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidUsernameOrPasswordException extends BadCredentialsException {
    public InvalidUsernameOrPasswordException(String msg) {
        super(msg);
    }
}
