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
import ru.api.category.dto.CategoryDto;
import ru.spring.shop.dao.CategoryDao;
import ru.spring.shop.entity.enums.Status;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryRestControllerIntegrationTest {

    private static final String SOFTWARE_CATEGORY_TITLE = "Software";
    private static final String HARDWARE_CATEGORY_TITLE = "Hardware";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title(SOFTWARE_CATEGORY_TITLE)
                                .status("ACTIVE")
                                .build()
                        )))
                .andExpect(status().isCreated());
        assertEquals(1, categoryDao.findAll().size());
    }

    @Test
    @Order(2)
    void findAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(SOFTWARE_CATEGORY_TITLE));
    }

    @Test
    @Order(3)
    void updateTest() throws Exception {
        mockMvc.perform(put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CategoryDto.builder()
                                .title(HARDWARE_CATEGORY_TITLE)
                                .status("ACTIVE")
                                .build()
                        )))
                .andExpect(status().isNoContent());

        assertEquals(HARDWARE_CATEGORY_TITLE, categoryDao.findById(1L).get().getTitle());
    }

    @Test
    @Order(4)
    void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/1"))
                .andExpect(status().isNoContent());

        assertEquals(Status.DISABLE, categoryDao.findById(1L).get().getStatus());
    }
}