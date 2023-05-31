package ru.spring.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.OrderItem;

public interface OrderItemDao  extends JpaRepository<OrderItem, Long> {


}
