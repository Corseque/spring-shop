package ru.spring.shop.convecters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.dao.ManufacturerDao;
import ru.spring.shop.web.mapper.ManufacturerMapper;

@Component
@RequiredArgsConstructor
public class ManufacturerConverter implements Converter<String, ManufacturerDto> {
    private final ManufacturerDao manufacturerDao;
    private final ManufacturerMapper manufacturerMapper;

    @Override
    public ManufacturerDto convert(String source) {
        return manufacturerMapper.toManufacturerDto(manufacturerDao.findById(Long.valueOf(source)).orElse(null));
        }
}
