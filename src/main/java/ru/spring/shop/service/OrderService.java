package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.api.order.dto.OrderDto;
import ru.api.order_item.dto.OrderItemDto;
import ru.spring.shop.dao.OrderDao;
import ru.spring.shop.entity.Order;
import ru.spring.shop.entity.OrderItem;
import ru.spring.shop.entity.enums.OrderStatus;
import ru.spring.shop.entity.security.AccountUser;
import ru.spring.shop.web.mapper.OrderItemMapper;
import ru.spring.shop.web.mapper.OrderMapper;
import ru.spring.shop.web.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderDao orderDao;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;

    @Transactional(readOnly = true)
    public List<OrderDto> findAll(){
        return orderDao.findAll().stream()
                .map(order -> orderMapper.toOrderDto(order))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAllByUser(AccountUser accountUser){
        return orderDao.findAllByAccountUser(accountUser).stream()
                .map(order -> orderMapper.toOrderDto(order))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order order = orderDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no order with id " + id));
        return orderMapper.toOrderDto(order);
    }

    public OrderDto save(OrderDto orderDto, Cart cart) {
        Order order = orderMapper.toOrder(orderDto, orderDao);
        order.setAccountUser(userService.findAccountUserByUsername(orderDto.getAccountUsername()));
        if (order.getId() != null) {
            orderDao.findById(orderDto.getId()).ifPresent(o ->
                    order.setVersion(o.getVersion())
            );
        }
        order.setStatus(OrderStatus.CREATED);
        Order savedOrder = orderDao.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        cart.getItems().forEach(item -> {
            item.setOrder(savedOrder);
            OrderItemDto orderItemDto = orderItemMapper.toOrderItemDto(item);
            orderItems.add(orderItemMapper.toOrderItem(orderItemService.save(orderItemDto), orderDao));
        });
        savedOrder.setOrderItems(orderItems);
        return orderMapper.toOrderDto(savedOrder);
    }

    public void updateStatus(Long id) {
        Optional<Order> order = orderDao.findById(id);
        order.ifPresent(o -> {
            OrderStatus status = o.getStatus();
            switch (status) {
                case PREPARING: o.setStatus(OrderStatus.CREATED);
                case CREATED: o.setStatus(OrderStatus.PROCESSING);
                case PROCESSING: o.setStatus(OrderStatus.DELIVERING);
                case DELIVERING: o.setStatus(OrderStatus.RECEIVED);
                case RECEIVED: o.setStatus(OrderStatus.CLOSED);
            }
            orderDao.save(o);
        });
    }

    public void deleteById(Long id) {
        Optional<Order> order = orderDao.findById(id);
        order.ifPresent(o -> {
            o.setStatus(OrderStatus.CANCELED);
            orderDao.save(o);
        });
    }
}
