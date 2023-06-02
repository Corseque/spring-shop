package ru.spring.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spring.shop.entity.ProductImage;

import java.util.Optional;

public interface ProductImageDao extends JpaRepository<ProductImage, Long> {

    @Query(value = "select product_image.path from product_image " +
            "where product_image.product_id = :id and " +
            "product_image.image_type = 'ICON_PICTURE' ORDER BY product_image.id DESC limit 1", nativeQuery = true)
    String findIconImageNameByProductId(@Param("id") Long id);

}
