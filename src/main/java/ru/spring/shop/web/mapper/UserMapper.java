package ru.spring.shop.web.mapper;

import org.mapstruct.Mapper;
import ru.api.security.dto.UserDto;
import ru.spring.shop.entity.security.AccountUser;

@Mapper
public interface UserMapper {
    AccountUser toAccountUser(UserDto userDto);
    UserDto toUserDto(AccountUser accountUser);
}
