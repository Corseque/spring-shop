package ru.spring.shop.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.service.ManufacturerService;
import ru.spring.shop.web.rest.ManufacturerRestController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ManufacturerRestController.class)
@Disabled
class ManufacturerRestControllerMockMvcTest {

    private static final String APPLE_COMPANY_NAME = "Apple";
    private static final String SAMSUNG_COMPANY_NAME = "Samsung";

    @MockBean
    ManufacturerService manufacturerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    List<ManufacturerDto> manufacturers;

    @BeforeEach
    void setUp() {
        manufacturers = new ArrayList<>();
        manufacturers.add(ManufacturerDto.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status("ACTIVE")
                .build());
        manufacturers.add(ManufacturerDto.builder()
                .id(2L)
                .name(SAMSUNG_COMPANY_NAME)
                .status("ACTIVE")
                .build());
    }

    @Test
    void findAllTest() throws Exception {

        Mockito.when(manufacturerService.findAll()).thenReturn(manufacturers);

        mockMvc.perform(get("/api/v1/manufacturer/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(APPLE_COMPANY_NAME))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].name").value(SAMSUNG_COMPANY_NAME));
    }

    @Test
    void saveTest() throws Exception {
        Mockito.when(manufacturerService.save(any(ManufacturerDto.class)))
                .thenReturn(ManufacturerDto.builder()
                        .id(1L)
                        .name(APPLE_COMPANY_NAME)
                        .status("ACTIVE")
                        .build());

        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ManufacturerDto.builder()
                                .name(APPLE_COMPANY_NAME)
                                .status("ACTIVE")
                                .build()
                )))
                .andExpect(status().isCreated());
    }

}