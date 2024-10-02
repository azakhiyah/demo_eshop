package com.azakhiyah.demo_eshop.service.order;

import com.azakhiyah.demo_eshop.dto.OrderDto;
import com.azakhiyah.demo_eshop.model.Order;
import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    
} 
