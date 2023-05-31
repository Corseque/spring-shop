package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.shop.web.model.Cart;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {

    public static final String CART_ATTRIBUTE ="cart";

    private final ProductService productService;

    public Cart getCurrentCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, Long productId) {
        productService.findProductById(productId)
                .ifPresent((p) -> getCurrentCart(session).add(p));
    }

    public void removeFromCart(HttpSession session, Long productId) {
        productService.findProductById(productId)
                .ifPresent((p) -> getCurrentCart(session).remove(p));
    }

    public BigDecimal getTotalPrice(HttpSession session) {
        return getCurrentCart(session).getTotalPrice();
    }

    public void resetCart(HttpSession session) {
        session.removeAttribute(CART_ATTRIBUTE);
    }
}
