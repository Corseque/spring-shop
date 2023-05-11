package ru.spring.shop.service;

import ru.api.security.AuthenticationUserDto;
import ru.api.security.UserDto;
import ru.spring.shop.entity.security.AccountUser;

import java.util.List;

public interface UserService {

    UserDto register(UserDto userDto);
    List<UserDto> findAll();
    UserDto findByName(String name);
    AccountUser findByUsername(String username);
    UserDto findById(Long id);
    UserDto update(UserDto userDto);
    void deleteById(Long id);

    //todo добавить метод обработки кода подтверждения(вопрос где хранить код в виду многопоточности)

    AccountUser findByUsernameAndPassword(AuthenticationUserDto authenticationUserDto);

}
