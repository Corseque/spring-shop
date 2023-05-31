package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.api.order_item.dto.OrderItemDto;
import ru.spring.shop.dao.OrderDao;
import ru.spring.shop.dao.OrderItemDao;
import ru.spring.shop.entity.OrderItem;
import ru.spring.shop.web.mapper.OrderItemMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderItemMapper orderItemMapper;
    private final OrderItemDao orderItemDao;
    private final OrderDao orderDao;

    public OrderItemDto save(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemMapper.toOrderItem(orderItemDto, orderDao);
//        if (orderItem.getId() != null) {
//            manufacturerDao.findById(manufacturer.getId()).ifPresent(m -> {
//                manufacturer.setVersion(m.getVersion());
//            });
//        }
        OrderItem savedItem = orderItemDao.save(orderItem);
        return orderItemMapper.toOrderItemDto(savedItem);
    }
}
