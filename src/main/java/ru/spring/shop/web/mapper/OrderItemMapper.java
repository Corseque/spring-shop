package ru.spring.shop.web.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.api.order_item.dto.OrderItemDto;
import ru.spring.shop.dao.OrderDao;
import ru.spring.shop.entity.OrderItem;

@Mapper(uses = ProductMapper.class)
public interface OrderItemMapper {

    OrderItem toOrderItem(OrderItemDto orderItemDto, @Context OrderDao orderDao);

    OrderItemDto toOrderItemDto(OrderItem orderItem);

}
