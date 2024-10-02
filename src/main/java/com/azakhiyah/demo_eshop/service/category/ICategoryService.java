package com.azakhiyah.demo_eshop.service.category;

import com.azakhiyah.demo_eshop.model.Category;
import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
    
}
