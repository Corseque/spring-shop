package ru.spring.shop.web.rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.api.manufacturer.dto.ManufacturerDto;
import ru.spring.shop.dao.ManufacturerDao;
import ru.spring.shop.entity.enums.Status;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManufacturerRestControllerIntegrationTest {

    private static final String APPLE_COMPANY_NAME = "Apple";
    private static final String SAMSUNG_COMPANY_NAME = "Samsung";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ManufacturerDao manufacturerDao;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void saveTest() throws Exception {


        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ManufacturerDto.builder()
                                .name(APPLE_COMPANY_NAME)
                                .status("ACTIVE")
                                .build()
                        )))
                .andExpect(status().isCreated());

        assertEquals(1, manufacturerDao.findAll().size());
    }

    @Test
    @Order(2)
    void findAllTest() throws Exception {

        mockMvc.perform(get("/api/v1/manufacturer/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(APPLE_COMPANY_NAME));
    }

    @Test
    @Order(3)
    void updateTest() throws Exception {
        mockMvc.perform(put("/api/v1/manufacturer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ManufacturerDto.builder()
                                .name(SAMSUNG_COMPANY_NAME)
                                .status("ACTIVE")
                                .build()
                        )))
                .andExpect(status().isNoContent());

        assertEquals(SAMSUNG_COMPANY_NAME, manufacturerDao.findById(1L).get().getName());
    }

    @Test
    @Order(4)
    void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/manufacturer/1"))
                .andExpect(status().isNoContent());

        assertEquals(Status.DISABLE, manufacturerDao.findById(1L).get().getStatus());
    }
    //todo тесты для продуктов рест
}