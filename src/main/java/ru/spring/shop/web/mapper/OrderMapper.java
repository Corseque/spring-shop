package ru.spring.shop.web.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.api.order.dto.OrderDto;
import ru.spring.shop.dao.OrderDao;
import ru.spring.shop.entity.Order;

@Mapper(uses = OrderItemMapper.class)
public interface OrderMapper {

    Order toOrder(OrderDto orderDto, @Context OrderDao orderDao);

    OrderDto toOrderDto(Order order);

}
