package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.models.Category;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("fakestore")
public class FakeStoreCategoryService implements CategoryService{
    @Override
    public Category createCategory(Category category) {
        return null;
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        return null;
    }

    @Override
    public Category replaceCategory(Long categoryId, Category category) {
        return null;
    }

    @Override
    public List<Category> getAllCategory() {
        return List.of();
    }

    @Override
    public Category getCategory(Long categoryId) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }
}
