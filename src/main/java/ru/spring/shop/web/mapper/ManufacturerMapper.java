package ru.spring.shop.web.mapper;

import org.mapstruct.Mapper;
import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.entity.Manufacturer;
import ru.spring.shop.entity.enums.Status;


@Mapper
public interface ManufacturerMapper {

    Manufacturer toManufacturer (ManufacturerDto manufacturerDto);

    ManufacturerDto toManufacturerDto (Manufacturer manufacturer);

    default Status getStatus(String status) {
        return Status.valueOf(status);
    }

    default String getStatus(Status status) {
        return status.getTitle();
    }
}
