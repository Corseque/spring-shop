package ru.spring.shop.exeption;

public class UserNotFoundException extends IllegalArgumentException{

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
