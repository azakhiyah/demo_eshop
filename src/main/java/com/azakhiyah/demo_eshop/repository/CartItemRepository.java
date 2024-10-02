package com.azakhiyah.demo_eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azakhiyah.demo_eshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
