package com.azakhiyah.demo_eshop.service.cart.cartItem;


import com.azakhiyah.demo_eshop.dto.CartItemDto;
import com.azakhiyah.demo_eshop.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
    CartItem getCartItemById(Long id);

    CartItemDto convertToDto(CartItem cartItem);
}
