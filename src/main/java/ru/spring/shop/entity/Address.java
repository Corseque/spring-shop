package ru.spring.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spring.shop.entity.common.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @OneToMany(mappedBy = "deliveryAddress", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Builder
    public Address(Long id, String country, String city, String street) {
        super(id);
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
