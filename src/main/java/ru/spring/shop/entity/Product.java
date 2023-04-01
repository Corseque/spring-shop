package ru.spring.shop.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;
import ru.spring.shop.entity.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Validated
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "manufacture_date")
    private LocalDate date;

    @Version
    @Column(name = "version")
    private int version;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String created_by;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime created_date;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String last_modified_by;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime last_modified_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Override
    public String toString() {
        StringBuilder printProduct = new StringBuilder("Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                ", version=" + version +
                ", created_by='" + created_by + '\'' +
                ", created_date=" + created_date +
                ", last_modified_by='" + last_modified_by + '\'' +
                ", last_modified_date=" + last_modified_date +
                ", status=" + status +
                ", manufacturer=" + manufacturer +
                ", categories=" +

                //categories. +
                '}');
        categories.forEach(category -> printProduct.append(category.getTitle()).append("; "));
        return printProduct.toString();
    }
}
