package com.azakhiyah.demo_eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azakhiyah.demo_eshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    
    boolean existsByName(String name);
    
}
