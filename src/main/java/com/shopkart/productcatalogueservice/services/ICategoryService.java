package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long categoryId, Category category);
    Category replaceCategory(Long categoryId, Category category);
    List<Category> getAllCategory();
    Category getCategory(Long categoryId);
    void deleteCategory(Long categoryId);
}
