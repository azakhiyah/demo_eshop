package com.azakhiyah.demo_eshop.service.cart;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.azakhiyah.demo_eshop.dto.CartDto;
import com.azakhiyah.demo_eshop.dto.CartItemDto;
import com.azakhiyah.demo_eshop.dto.ImageDto;
import com.azakhiyah.demo_eshop.dto.ProductDto;
import com.azakhiyah.demo_eshop.exceptions.ResourceNotFoundException;
import com.azakhiyah.demo_eshop.model.Cart;
import com.azakhiyah.demo_eshop.model.Product;
import com.azakhiyah.demo_eshop.repository.CartItemRepository;
import com.azakhiyah.demo_eshop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.stream.Collectors;


import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    private final ModelMapper modelMapper;

    @Override
    public Cart getCart(Long id) {
         Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        
    }

    @Override
    public CartDto getCartDtoById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
       
        // Convert Cart to CartDto
        CartDto cartDto = modelMapper.map(cart, CartDto.class);

        // Map each CartItem to CartItemDto
        List<CartItemDto> cartItemDtos = cart.getItems().stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);

                    // Map Product and its images
                    Product product = cartItem.getProduct();
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);

                    // Map images
                    List<ImageDto> imageDtos = product.getImages().stream()
                            .map(image -> modelMapper.map(image, ImageDto.class))
                            .collect(Collectors.toList());

                    productDto.setImages(imageDtos);
                    cartItemDto.setProduct(productDto);
                    return cartItemDto;
                })
                .collect(Collectors.toList());

        // Set the list of items in the CartDto
        cartDto.setItems(cartItemDtos);       
        return cartDto;

    }
    

}
