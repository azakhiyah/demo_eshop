package com.azakhiyah.demo_eshop.dto;

import java.math.BigDecimal;
import lombok.Data;
import java.util.List;
@Data
public class CartDto {
    private Long cartId;
    //private Set<CartItemDto> items;
    private BigDecimal totalAmount;
    private List<CartItemDto> items;  
    //private CartItemDto cartItemDto;
}
