package com.azakhiyah.demo_eshop.service.cart;


import com.azakhiyah.demo_eshop.dto.CartDto;
import com.azakhiyah.demo_eshop.model.Cart;
import java.math.BigDecimal;


public interface ICartService {
    Cart getCart(Long id);
    Cart getCartById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);

    CartDto getCartDtoById(Long id);

}
