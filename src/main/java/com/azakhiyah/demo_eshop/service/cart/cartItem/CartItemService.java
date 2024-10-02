package com.azakhiyah.demo_eshop.service.cart.cartItem;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.azakhiyah.demo_eshop.dto.CartItemDto;
import com.azakhiyah.demo_eshop.dto.ImageDto;
import com.azakhiyah.demo_eshop.dto.ProductDto;
import com.azakhiyah.demo_eshop.exceptions.ResourceNotFoundException;
import com.azakhiyah.demo_eshop.model.Cart;
import com.azakhiyah.demo_eshop.model.CartItem;
import com.azakhiyah.demo_eshop.model.Image;
import com.azakhiyah.demo_eshop.model.Product;
import com.azakhiyah.demo_eshop.repository.CartItemRepository;
import com.azakhiyah.demo_eshop.repository.CartRepository;
import com.azakhiyah.demo_eshop.repository.ImageRepository;
import com.azakhiyah.demo_eshop.service.cart.ICartService;
import com.azakhiyah.demo_eshop.service.product.IProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ImageRepository imageRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final ModelMapper modelMapper;


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    
    }

    @Override
    public CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);

        // Convert Product to ProductDto
        ProductDto productDto = modelMapper.map(cartItem.getProduct(), ProductDto.class);
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'convertToDto'");
        List<Image> images = imageRepository.findByProductId(cartItem.getProduct().getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toList());

        // Set images to ProductDto
        productDto.setImages(imageDtos);

        // Set productDto to cartItemDto
        cartItemDto.setProduct(productDto);

        return cartItemDto;
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("CartItem not found"));
        
    }
    
}
