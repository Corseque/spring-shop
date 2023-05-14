package ru.spring.shop.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;
import ru.spring.shop.entity.common.InfoEntity;
import ru.spring.shop.entity.enums.Status;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Validated
@Table(name = "manufacturer")
@EntityListeners(AuditingEntityListener.class)
public class Manufacturer extends InfoEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Product> products;






    @Override
    public String toString() {
        StringBuilder printManufacturer = new StringBuilder("Category{" +
                "id=" + getId() + '\'' +
                "name='" + name + '\'' +
                ", products=");
        products.forEach(product -> printManufacturer.append(product.getTitle()).append("; "));
        printManufacturer.append('}');
        return printManufacturer.toString();
    }

    @Builder

    public Manufacturer(Long id, int version, String created_by, LocalDateTime created_date, String last_modified_by,
                        LocalDateTime last_modified_date, Status status, String name, Set<Product> products) {
        super(id, version, created_by, created_date, last_modified_by, last_modified_date, status);
        this.name = name;
        this.products = products;
    }
}
