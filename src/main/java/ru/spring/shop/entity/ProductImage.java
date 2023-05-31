package ru.spring.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spring.shop.entity.common.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product_image")
public class ProductImage extends BaseEntity {

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductImage(Long id, String path, Product product) {
        super(id);
        this.path = path;
        this.product = product;
    }
}
