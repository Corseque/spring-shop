package ru.spring.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.Manufacturer;

import java.util.Optional;

public interface ManufacturerDao extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByName (String name);


}
