package ru.spring.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.dao.ManufacturerDao;
import ru.spring.shop.entity.Manufacturer;
import ru.spring.shop.entity.enums.Status;
import ru.spring.shop.web.mapper.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceMockitoTest {

    private static final String APPLE_COMPANY_NAME = "Apple";
    private static final String SAMSUNG_COMPANY_NAME = "Samsung";

    @Mock
    ManufacturerDao manufacturerDao;

    @Mock
    ManufacturerMapper manufacturerMapper;

    @InjectMocks
    ManufacturerService manufacturerService;

    List<Manufacturer> manufacturers;

    @BeforeEach
    void setUp() {
        manufacturers = new ArrayList<>();
        manufacturers.add(Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build());
        manufacturers.add(Manufacturer.builder()
                .id(2L)
                .name(SAMSUNG_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build());

    }

    @Test
    void findAllTest() {
        // given
        given(manufacturerDao.findAll()).willReturn(manufacturers);
        given(manufacturerMapper.toManufacturerDto(any())).will(
                (invocation) -> {
                    Manufacturer manufacturer = invocation.getArgument(0);
                    if (manufacturer == null) {
                        return null;
                    }
                    return ManufacturerDto.builder()
                            .id(manufacturer.getId())
                            .name(manufacturer.getName())
                            .build();
                });
        final int manufacturersSize = manufacturers.size();

        // when
        List<ManufacturerDto> manufacturerList = manufacturerService.findAll();

        // then
        then(manufacturerDao).should().findAll();

        assertAll(
                () -> assertEquals(manufacturersSize, manufacturerList.size(), "Size must be equals " + manufacturersSize),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturerList.get(0).getName())
        );
    }


    //todo методы сохранения и удаления

    @Test
    void saveTest() {
        // given
        ManufacturerDto manufacturerDto = ManufacturerDto.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status("ACTIVE")
                .build();

        given(manufacturerMapper.toManufacturerDto(any())).will(
                (invocation) -> {
                    Manufacturer mappedManufacturer = invocation.getArgument(0);
                    if (mappedManufacturer == null) {
                        return null;
                    }
                    return ManufacturerDto.builder()
                            .id(mappedManufacturer.getId())
                            .name(mappedManufacturer.getName())
                            .build();
                });

        given(manufacturerMapper.toManufacturer(any())).will(
                (invocation) -> {
                    ManufacturerDto mappedManufacturerDto = invocation.getArgument(0);
                    if (mappedManufacturerDto == null) {
                        return null;
                    }
                    return Manufacturer.builder()
                            .id(mappedManufacturerDto.getId())
                            .name(mappedManufacturerDto.getName())
                            .build();
                });

        // when
        manufacturerService.save(manufacturerDto);

        // then
        then(manufacturerDao).should().findById(manufacturerDto.getId());
        then(manufacturerDao).should().save(any());
    }

    @Test
    void deleteByIdTest() {
        ManufacturerDto manufacturerDto = ManufacturerDto.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status("ACTIVE")
                .build();
        manufacturerService.deleteById(manufacturerDto.getId());
        then(manufacturerDao).should().findById(manufacturerDto.getId());
    }
}