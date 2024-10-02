package com.azakhiyah.demo_eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


import com.azakhiyah.demo_eshop.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);
    
}
