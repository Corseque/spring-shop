package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.api.address.dto.AddressDto;
import ru.spring.shop.dao.AddressDao;
import ru.spring.shop.entity.Address;
import ru.spring.shop.web.mapper.AddressMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressMapper addressMapper;

    private final AddressDao addressDao;

    public AddressDto save(AddressDto addressDto) {
        Address address = addressMapper.toAddress(addressDto);
//        if (address.getId() != null) {
//            addressDao.findById(addressDto.getId()).ifPresent(a ->
//                    address.setVersion(a.getVersion())
//            );
//        }

        return addressMapper.toAddressDto(addressDao.save(address));
    }
}
