package com.azakhiyah.demo_eshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.azakhiyah.demo_eshop.dto.CartItemDto;
import com.azakhiyah.demo_eshop.exceptions.ResourceNotFoundException;
import com.azakhiyah.demo_eshop.model.Cart;
import com.azakhiyah.demo_eshop.model.CartItem;
import com.azakhiyah.demo_eshop.model.User;
import com.azakhiyah.demo_eshop.response.ApiResponse;
import com.azakhiyah.demo_eshop.service.cart.ICartService;
import com.azakhiyah.demo_eshop.service.cart.cartItem.ICartItemService;
import com.azakhiyah.demo_eshop.service.user.UserService;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final UserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            if (cartId == null) {
              cartId= cartService.initializeNewCart();
            }
            //User user = userService.getUserById(1L);
            //Cart cart = cartService.initializeNewCart(user);
            
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                           @PathVariable Long itemId,
                                                           @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/cart/{cartId}/items")
    public  ResponseEntity<ApiResponse> getCartItems(@PathVariable Long cartId) {
        try {
        CartItem cartItem = cartItemService.getCartItemById(cartId);
        CartItemDto cartItemDto = cartItemService.convertToDto(cartItem);
        return ResponseEntity.ok(new ApiResponse("success", cartItemDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
