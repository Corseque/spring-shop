package ru.spring.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.Product;

import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {

    Optional<Product> findByTitle(String title);
}
