package ru.spring.shop.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;
import ru.spring.shop.entity.common.InfoEntity;
import ru.spring.shop.entity.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Validated
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product extends InfoEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "manufacture_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "product")
    private List<ProductImage> images;

    public void addImage(ProductImage productImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(productImage);
    }

    @Override
    public String toString() {
        StringBuilder printProduct = new StringBuilder("Product{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                ", manufacturer=" + manufacturer.getName() +
                ", categories=" +
                '}');
        categories.forEach(category -> printProduct.append(category.getTitle()).append("; "));
        printProduct.append('}');
        return printProduct.toString();
    }

    @Builder
    public Product(Long id, int version, String created_by, LocalDateTime created_date, String last_modified_by,
                   LocalDateTime last_modified_date, String title, BigDecimal cost, LocalDate date,
                   Manufacturer manufacturer, Set<Category> categories, Status status, List<ProductImage> images) {
        super(id, version, created_by, created_date, last_modified_by, last_modified_date);
        this.title = title;
        this.cost = cost;
        this.date = date;
        this.manufacturer = manufacturer;
        this.categories = categories;
        this.status = status;
        this.images = images;
    }
}
