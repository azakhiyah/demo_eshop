package com.azakhiyah.demo_eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azakhiyah.demo_eshop.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    boolean existsByEmail(String email);
    
} 
