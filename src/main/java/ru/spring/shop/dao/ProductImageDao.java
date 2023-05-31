package ru.spring.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spring.shop.entity.ProductImage;

public interface ProductImageDao extends JpaRepository<ProductImage, Long> {

    @Query(value = "select product_image.path from product_image " +
            "where product_image.product_id = :id limit 1", nativeQuery = true)
    String findImageNameByProductId(@Param("id") Long id);

    //todo дз 11 метод получение всех изобрпажений
}
