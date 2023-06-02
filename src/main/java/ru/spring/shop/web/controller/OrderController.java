package ru.spring.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.api.address.dto.AddressDto;
import ru.api.order.dto.OrderDto;
import ru.spring.shop.entity.enums.OrderStatus;
import ru.spring.shop.entity.security.AccountUser;
import ru.spring.shop.security.JpaUserDetailService;
import ru.spring.shop.service.CartService;
import ru.spring.shop.service.OrderService;
import ru.spring.shop.web.mapper.OrderItemMapper;
import ru.spring.shop.web.model.Cart;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final CartService cartService;
    private final JpaUserDetailService jpaUserDetailService;
    private final OrderService orderService;

    @GetMapping("/fill")
    public String fillOrder(Model model, HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountUser accountUser = jpaUserDetailService.findAccountUserByUsername(authentication.getName());
        Cart cart = cartService.getCurrentCart(httpSession);
        OrderDto preorder = OrderDto.builder()
                .recipientFirstname(accountUser.getFirstname())
                .recipientLastname(accountUser.getLastname())
                .recipientPhone(accountUser.getPhone())
                .recipientMail(accountUser.getEmail())
                .status(OrderStatus.PREPARING.name())
                .deliveryDate(LocalDate.now().plusDays(2))
                .deliveryPrice(BigDecimal.valueOf(100.00))
                .deliveryAddress(new AddressDto())
                .accountUsername(accountUser.getUsername())
                .price(cart.getTotalPrice())
                .build();
        model.addAttribute("preorder", preorder);
        model.addAttribute("cart", cart);
        return "order/order-form";
    }

    @PostMapping("/create")
    public String placeOrder(Model model, OrderDto orderDto, HttpSession httpSession) {
        Cart cart = cartService.getCurrentCart(httpSession);
        orderDto.setPrice(cart.getTotalPrice());
        OrderDto savedOrderDto = orderService.save(orderDto, cart);
        cart.clear();
        model.addAttribute("order", savedOrderDto);
        return "order/order-success";
    }


}
