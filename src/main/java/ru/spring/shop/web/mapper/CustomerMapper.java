//package ru.spring.shop.web.mapper;
//
//import org.mapstruct.Context;
//import org.mapstruct.Mapper;
//import ru.api.security.dto.CustomerDto;
//import ru.spring.shop.dao.OrderDao;
//import ru.spring.shop.entity.Order;
//import ru.spring.shop.entity.security.AccountUser;
//
//import java.util.NoSuchElementException;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Mapper
//public interface CustomerMapper {
//
//    default CustomerDto toCustomerDto(AccountUser accountUser, @Context OrderDao orderDao) {
//        if (accountUser == null) {
//            return null;
//        }
//        return CustomerDto.builder()
//                .id(accountUser.getId())
//                .username(accountUser.getUsername())
//                .firstname(accountUser.getFirstname())
//                .lastname(accountUser.getLastname())
//                .email(accountUser.getEmail())
//                .phone(accountUser.getPhone())
//                .orderIds(orderDao.findAllByAccountUser(accountUser).stream()
//                        .map(order -> order.getId())
//                        .collect(Collectors.toSet()))
//                .build();
//    }
////
////    AccountUser toAccountUser(CustomerDto customerDto);
////
////    default Set<Long> getOrders(Set<Order> orders, @Context OrderDao orderDao) {
////        return orders.stream()
////                .map(order -> order.getId())
////                .collect(Collectors.toSet());
////    }
//
//    //
////    default Set<Order> getOrders(Set<Long> orderIds, @Context OrderDao orderDao) {
////        return orderIds.stream()
////                .map(orderId -> orderDao.findById(orderId).orElseThrow(() -> new NoSuchElementException("There is no order item with id "
////                            + orderId)))
////                .collect(Collectors.toSet());
////    }
//}
