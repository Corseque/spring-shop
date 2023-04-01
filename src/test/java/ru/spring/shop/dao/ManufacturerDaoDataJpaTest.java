package ru.spring.shop.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.spring.shop.config.ShopConfig;
import ru.spring.shop.entity.Manufacturer;
import ru.spring.shop.entity.enums.Status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ShopConfig.class)
class ManufacturerDaoDataJpaTest {

    @Autowired
    ManufacturerDao manufacturerDao;

    @Autowired
    TestEntityManager entityManager;

    private static final String APPLE_COMPANY_NAME = "Apple";
    private static final String SAMSUNG_COMPANY_NAME = "Samsung";
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
        for (Manufacturer manufacturer : manufacturers) {
            entityManager.merge(manufacturer);
        }
    }

    @Test
    void saveTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build();

        Manufacturer savedManufacturer = manufacturerDao.save(manufacturer);

        //todo разобраться, почему не сохраняется информация о создании и изменении
        System.out.println(savedManufacturer.getName());
        System.out.println(savedManufacturer.getCreated_by());
        System.out.println(savedManufacturer.getCreated_date());
        System.out.println(savedManufacturer.getLast_modified_by());
        System.out.println(savedManufacturer.getLast_modified_date());


        assertAll(
                () -> assertEquals(1L, savedManufacturer.getId()),
                () -> assertEquals(APPLE_COMPANY_NAME, savedManufacturer.getName()),
                () -> assertEquals(0, savedManufacturer.getVersion())//,
//                () -> assertEquals("User", savedManufacturer.getCreated_by()),
//                () -> assertNotNull(savedManufacturer.getCreated_date()),
//                () -> assertEquals("User", savedManufacturer.getLast_modified_by()),
//                () -> assertNotNull(savedManufacturer.getLast_modified_date())
//                , разобраться со статусом?
//                () -> assertEquals("ACTIVE", savedManufacturer.getStatus())
        );
    }

    @Test
    void saveWithEntityManagerTest() {
        Manufacturer manufacturer = Manufacturer.builder()
                .id(1L)
                .name(APPLE_COMPANY_NAME)
                .status(Status.ACTIVE)
                .build();
        Manufacturer savedManufacturer = entityManager.merge(manufacturer);
        Long id = savedManufacturer.getId();

        assertAll(
                () -> assertEquals(id, savedManufacturer.getId(), "id"),
                () -> assertEquals(APPLE_COMPANY_NAME, savedManufacturer.getName(), "name"),
                () -> assertEquals(0, savedManufacturer.getVersion(), "version")
//                ,
//                () -> assertEquals("User", savedManufacturer.getCreated_by()),
//                () -> assertNotNull(savedManufacturer.getCreated_date()),
//                () -> assertEquals("User", savedManufacturer.getLast_modified_by()),
//                () -> assertNotNull(savedManufacturer.getLast_modified_date())
//                , разобраться со статусом?
//                () -> assertEquals("ACTIVE", savedManufacturer.getStatus())
        );
    }

    //todo findAll, update, delete
    //todo* попробовать через persistenceContext сохранить (EntityManager || Autowired)

    @Test
    void findAllWithEntityManagerTest() {
        int size = manufacturers.size();
        assertAll(
                () -> assertEquals(size, manufacturerDao.findAll().size(), "Size should be " + size)
        );
    }

    @Test
    void updateWithEntityManagerTest() {
        Manufacturer manufacturer = manufacturerDao.findByName(APPLE_COMPANY_NAME).get();
        manufacturer.setName(SAMSUNG_COMPANY_NAME);
        entityManager.merge(manufacturer);
        Long id = manufacturer.getId();

        assertAll(
                () -> assertEquals(SAMSUNG_COMPANY_NAME, manufacturerDao.findById(id).get().getName(), "Name should be " + SAMSUNG_COMPANY_NAME)
        );
    }

    @Test
    void deleteWithEntityManagerTest() {
        entityManager.remove(manufacturerDao.findByName(APPLE_COMPANY_NAME).get());
        int size = manufacturerDao.findAll().size();

        assertAll(
                () -> assertEquals(size, manufacturerDao.findAll().size(), "Size should be " + size),
                () -> assertEquals(SAMSUNG_COMPANY_NAME, manufacturerDao.findAll().get(0).getName(), "Name should be " + SAMSUNG_COMPANY_NAME)
        );
    }
}