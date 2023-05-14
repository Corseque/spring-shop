package ru.spring.shop.service;

public interface ConfirmationService {

    boolean confirmRegistration(String token, String username);
    void createToken(String username);


}
