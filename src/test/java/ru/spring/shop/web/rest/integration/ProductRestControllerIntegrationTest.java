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
import ru.spring.shop.dao.CategoryDao;
import ru.spring.shop.dao.ManufacturerDao;
import ru.spring.shop.dao.ProductDao;
import ru.spring.shop.entity.Category;
import ru.spring.shop.entity.Manufacturer;
import ru.spring.shop.entity.enums.Status;
import ru.spring.shop.web.dto.ProductDto;
import ru.spring.shop.web.dto.mapper.CategoryMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRestControllerIntegrationTest {

    private static final String SMARTPHONE_PRODUCT_TITLE = "Smartphone";
    private static final String WATCH_PRODUCT_TITLE = "Watch";
    private static final String SOFTWARE_CATEGORY_TITLE = "Software";
    private static final String HARDWARE_CATEGORY_TITLE = "Hardware";
    private static final String APPLE_COMPANY_NAME = "Apple";
    private static final String SAMSUNG_COMPANY_NAME = "Samsung";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductDao productDao;

    @Autowired
    ManufacturerDao manufacturerDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void saveTest() throws Exception {

        Manufacturer savedManufacturer = manufacturerDao.save(Manufacturer.builder()
                .name(APPLE_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build());

        Category savedCategory = categoryDao.save(Category.builder()
                .title(HARDWARE_CATEGORY_TITLE)
                .status(Status.ACTIVE)
                .build());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.builder()
                                .title(SMARTPHONE_PRODUCT_TITLE)
                                .cost(new BigDecimal("100.00"))
                                .date(LocalDate.now())
                                .status(Status.ACTIVE)
                                .manufacturer(savedManufacturer.getName())
                                .categories(Set.of(categoryMapper.toCategoryDto(savedCategory)))
                                .build()
                        )))
                .andExpect(status().isCreated());

        assertEquals(1, productDao.findAll().size());
        assertEquals(SMARTPHONE_PRODUCT_TITLE, productDao.findById(1L).get().getTitle());
    }

    //todo написать интеграционный тест
}