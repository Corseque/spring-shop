package ru.spring.shop.web.mapper;

import org.mapstruct.Mapper;
import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.entity.Manufacturer;


@Mapper
public interface ManufacturerMapper {

    Manufacturer toManufacturer (ManufacturerDto manufacturerDto);

    ManufacturerDto toManufacturerDto (Manufacturer manufacturer);

}
