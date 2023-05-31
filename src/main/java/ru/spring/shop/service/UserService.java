package ru.spring.shop.service;

import ru.api.security.dto.AuthenticationUserDto;
//import ru.api.security.dto.CustomerDto;
import ru.api.security.dto.UserDto;
import ru.spring.shop.entity.security.AccountUser;

import java.util.List;

public interface UserService {

    UserDto register(UserDto userDto);
    List<UserDto> findAll();
    UserDto findUserDtoByName(String name);
//    CustomerDto getCustomerDto(AccountUser accountUser);
    AccountUser findAccountUserByUsername(String username);
    UserDto findById(Long id);
    UserDto update(UserDto userDto);
    void deleteById(Long id);
    AccountUser findByUsernameAndPassword(AuthenticationUserDto authenticationUserDto);

}
