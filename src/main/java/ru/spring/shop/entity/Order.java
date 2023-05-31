package ru.spring.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;
import ru.spring.shop.entity.common.InfoEntity;
import ru.spring.shop.entity.enums.OrderStatus;
import ru.spring.shop.entity.security.AccountUser;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Validated
@Entity
@Table(name = "shop_order")
@EntityListeners(AuditingEntityListener.class)
public class Order extends InfoEntity {
    @Column(name = "recipient_firstname")
    private String recipientFirstname;

    @Column(name = "recipient_lastname")
    private String recipientLastname;

    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Column(name = "recipient_mail")
    private String recipientMail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

//    @ManyToOne
//    @JoinColumn(name = "shop_address_id")
//    private Address shopAddress;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AccountUser accountUser;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Builder
    public Order(Long id, int version, String created_by, LocalDateTime created_date, String last_modified_by,
                 LocalDateTime last_modified_date, String recipientFirstname, String recipientLastname,
                 String recipientPhone, String recipientMail, OrderStatus status, LocalDate deliveryDate,
                 BigDecimal deliveryPrice, Address deliveryAddress,
//                 Address shopAddress,
                 AccountUser accountUser,
                 BigDecimal price, List<OrderItem> orderItems) {
        super(id, version, created_by, created_date, last_modified_by, last_modified_date);
        this.recipientFirstname = recipientFirstname;
        this.recipientLastname = recipientLastname;
        this.recipientPhone = recipientPhone;
        this.recipientMail = recipientMail;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
        this.deliveryAddress = deliveryAddress;
//        this.shopAddress = shopAddress;
        this.accountUser = accountUser;
        this.price = price;
        this.orderItems = orderItems;
    }
}
