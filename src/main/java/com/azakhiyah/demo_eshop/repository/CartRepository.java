package com.azakhiyah.demo_eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azakhiyah.demo_eshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
        Cart findByUserId(Long userId);
        //Cart findById(Long Id);
}
