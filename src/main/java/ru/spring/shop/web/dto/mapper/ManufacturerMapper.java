package ru.spring.shop.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.spring.shop.entity.Manufacturer;
import ru.spring.shop.web.dto.ManufacturerDto;


@Mapper
public interface ManufacturerMapper {

    Manufacturer toManufacturer (ManufacturerDto manufacturerDto);

    ManufacturerDto toManufacturerDto (Manufacturer manufacturer);
}
