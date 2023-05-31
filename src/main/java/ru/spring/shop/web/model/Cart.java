package ru.spring.shop.web.model;

import lombok.Data;
import ru.spring.shop.entity.OrderItem;
import ru.spring.shop.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<OrderItem> items;
    private BigDecimal totalPrice;
    private int totalQuantity;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
    }

    public void add(final Product product) {
        OrderItem orderItem = findOrderItemByProduct(product);
        if (orderItem == null) {
            orderItem = OrderItem.builder()
                    .product(product)
                    .itemPrice(product.getCost())
                    .quantity((short) 0)
                    .totalPrice(BigDecimal.ZERO)
                    .build();
            items.add(orderItem);
        }
        orderItem.setQuantity((short) (orderItem.getQuantity() + 1));
        recalculate();
    }

    public void remove(Product product) {
        OrderItem orderItem = findOrderItemByProduct(product);
        if (orderItem == null) {
            return;
        }
        items.remove(orderItem);
        recalculate();
    }

    public void clear() {
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;

        for (OrderItem item : items) {
            item.setTotalPrice(item.getProduct().getCost().multiply(new BigDecimal(item.getQuantity())));
            totalPrice = totalPrice.add(item.getTotalPrice());
            totalQuantity += item.getQuantity();
        }

    }

    private OrderItem findOrderItemByProduct(final Product product) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
    }


}
