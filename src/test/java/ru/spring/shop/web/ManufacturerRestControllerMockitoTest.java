package ru.spring.shop.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.spring.shop.entity.enums.Status;
import ru.spring.shop.service.ManufacturerService;
import ru.spring.shop.web.dto.ManufacturerDto;
import ru.spring.shop.web.rest.ManufacturerRestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerRestControllerMockitoTest {

    private static final String APPLE_COMPANY_NAME = "Apple";
    private static final String SAMSUNG_COMPANY_NAME = "Samsung";

    @Mock
    ManufacturerService manufacturerService;

    @InjectMocks
    ManufacturerRestController manufacturerRestController;


    MockMvc mockMvc;

    List<ManufacturerDto> manufacturers;

    @BeforeEach
    void setUp() {
        manufacturers = new ArrayList<>();
        manufacturers.add(ManufacturerDto.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build());
        manufacturers.add(ManufacturerDto.builder()
                .id(2L)
                .name(SAMSUNG_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build());
        mockMvc = MockMvcBuilders.standaloneSetup(manufacturerRestController).build();
    }

    @Test
    void getManufacturerListTest() {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturers);
        final int size = manufacturers.size();

        // when
        List<ManufacturerDto> manufacturerList = manufacturerRestController.getManufacturerList();

        // then
        then(manufacturerService).should().findAll();

        assertAll(
                () -> assertEquals(size, manufacturerList.size(), "List size = " + size),
                () -> assertEquals(APPLE_COMPANY_NAME, manufacturerList.get(0).getName(), "First company = " + APPLE_COMPANY_NAME)
        );
    }

    @Test
    void addManufacturerTest() {
        ManufacturerDto manufacturer = ManufacturerDto.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build();
        given(manufacturerService.save(any())).will(
                (invocation) -> {
                    ManufacturerDto manufacturerDto = invocation.getArgument(0);

                    if (manufacturerDto == null) {
                        return null;
                    }

                    return ManufacturerDto.builder()
                            .id(manufacturerDto.getId())
                            .name(manufacturerDto.getName())
                            .build();
                });
        URI location = URI.create("/api/v1/manufacturer/" + manufacturer.getId());

        ResponseEntity<?> responseEntity = manufacturerRestController.addManufacturer(manufacturer);

        then(manufacturerService).should().save(any());

        assertAll(
                () -> assertEquals(location, responseEntity.getHeaders().getLocation()),
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode())
        );
    }

    @Test
    void deleteByIdTest() {
        ManufacturerDto manufacturer = ManufacturerDto.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build();
        Long id = manufacturer.getId();

        manufacturerRestController.deleteById(id);

        then(manufacturerService).should().deleteById(id);
    }

    // todo методы сохранения и удаления (unit тестами и mocMvc)


    @Test
    void getManufacturerListMocMvcTest() throws Exception {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturers);

        mockMvc.perform(get("/api/v1/manufacturer/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(APPLE_COMPANY_NAME))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].name").value(SAMSUNG_COMPANY_NAME));
    }

    @Test
    void addManufacturerMocMvcTest() throws Exception {

        given(manufacturerService.save(any())).will(
                (invocation) -> {
                    ManufacturerDto manufacturerDto = invocation.getArgument(0);

                    if (manufacturerDto == null) {
                        return null;
                    }

                    return ManufacturerDto.builder()
                            .id(manufacturerDto.getId())
                            .name(manufacturerDto.getName())
                            .build();
                });

        String json = "{\"name\": \"Apple\", \"status\": \"ACTIVE\"}";


        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

    }

    @Test
    void deleteMocMvcTest() throws Exception {
        mockMvc.perform(delete("/api/v1/manufacturer/{manufacturerId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void saveMocMvcTest() throws Exception {
        given(manufacturerService.save(any())).will(
                (invocation) -> {
                    ManufacturerDto manufacturerDto = invocation.getArgument(0);

                    if (manufacturerDto == null) {
                        return null;
                    }

                    return ManufacturerDto.builder()
                            .id(manufacturerDto.getId())
                            .name(manufacturerDto.getName())
                            .build();
                });

        String json = "{\"name\": \"Samsung\", \"status\": \"ACTIVE\"}";
        Long id = manufacturers.get(0).getId();

        mockMvc.perform(put("/api/v1/manufacturer/{manufacturerId}", id)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());
    }

}