package ru.spring.shop.web.mapper;

import org.mapstruct.Mapper;
import ru.api.address.dto.AddressDto;
import ru.spring.shop.entity.Address;

@Mapper
public interface AddressMapper {

    Address toAddress(AddressDto addressDto);

    AddressDto toAddressDto(Address address);
}
