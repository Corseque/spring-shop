package ru.spring.shop.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;
import ru.spring.shop.entity.common.InfoEntity;
import ru.spring.shop.entity.enums.Status;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Validated
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class Category extends InfoEntity {

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @Override
    public String toString() {
        StringBuilder printCategory = new StringBuilder("Category{" +
                "id=" + getId() + '\'' +
                "title='" + title + '\'' +
                ", products=");
        products.forEach(product -> printCategory.append(product.getTitle()).append("; "));
        printCategory.append('}');
        return printCategory.toString();
    }

    @Builder
    public Category(Long id, int version, String created_by, LocalDateTime created_date, String last_modified_by,
                    LocalDateTime last_modified_date, Status status, String title, Set<Product> products) {
        super(id, version, created_by, created_date, last_modified_by, last_modified_date);
        this.title = title;
        this.products = products;
        this.status = status;
    }
}
