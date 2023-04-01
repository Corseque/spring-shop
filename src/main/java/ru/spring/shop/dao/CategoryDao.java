package ru.spring.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.Category;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> findByTitle(String title);
}
