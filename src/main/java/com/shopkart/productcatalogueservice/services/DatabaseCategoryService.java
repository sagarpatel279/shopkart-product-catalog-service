package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.State;
import com.shopkart.productcatalogueservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("database")
public class DatabaseCategoryService implements CategoryService{
    private CategoryRepository categoryRepository;

    @Autowired
    public DatabaseCategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
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
        return categoryRepository.findAllByState(State.ACTIVE);
    }

    @Override
    public Category getCategory(Long categoryId) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }
}
