package com.azakhiyah.demo_eshop.request;

import com.azakhiyah.demo_eshop.model.Category;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
