package com.shopkart.productcatalogueservice.services;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.shopkart.productcatalogueservice.exceptions.CategoryAlreadyExistException;
import com.shopkart.productcatalogueservice.exceptions.CategoryDeletionException;
import com.shopkart.productcatalogueservice.exceptions.CategoryNotFoundException;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.State;
import com.shopkart.productcatalogueservice.repositories.CategoryRepository;
import com.shopkart.productcatalogueservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Profile("database")
public class DatabaseCategoryService implements CategoryService{
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public DatabaseCategoryService(CategoryRepository categoryRepository,ProductRepository productRepository){
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
    }
    @Override
    public Category createCategory(Category category) {
        boolean categoryExist=categoryRepository.existByNameAndState(category.getName(),State.ACTIVE);
        if(categoryExist)
            throw new CategoryAlreadyExistException("Category is already exist with given name");
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryOptional=categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty() ||  categoryOptional.get().getState()!=State.ACTIVE)
            throw new CategoryNotFoundException("Category could not be found by given id");
        Category categoryFromDb=categoryOptional.get();

        if(category.getName()!=null && !category.getName().isBlank())
            categoryFromDb.setName(category.getName());
        if(category.getDescription()!=null && !category.getDescription().isBlank())
            categoryFromDb.setDescription(category.getDescription());
        categoryFromDb.setUpdatedAt(new Date());
        return categoryRepository.save(categoryFromDb);
    }

    @Override
    public Category replaceCategory(Long categoryId, Category category) {
        boolean categoryExist= categoryRepository.existsByIdAndState(categoryId,State.ACTIVE);
        if(!categoryExist)
            throw new CategoryNotFoundException("Category could not be found by given Id");
        category.setId(categoryId);
        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAllByState(State.ACTIVE);
    }

    @Override
    public Category getCategory(Long categoryId) {
        Optional<Category> categoryOptional=categoryRepository.findByIdAndState(categoryId,State.ACTIVE);
        if(categoryOptional.isEmpty())
            throw new CategoryNotFoundException("Category could not be found by given Id");
        return categoryOptional.get();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category=categoryRepository.findByIdAndState(categoryId,State.ACTIVE)
                .orElseThrow(()-> new CategoryNotFoundException("Category could not be found by given Id"));
        boolean productsByCategoryExist=productRepository.existByCategory_IdAndState(categoryId,State.ACTIVE);
        if(productsByCategoryExist)
            throw new CategoryDeletionException("Category could not be deleted because products associated with category are exist");
        category.setState(State.DELETED);
        category.setUpdatedAt(new Date());
        categoryRepository.save(category);
    }
}
