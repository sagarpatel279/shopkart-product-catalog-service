package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.dtos.records.db.CategoryIdNameDescriptionDbRecord;
import com.shopkart.productcatalogueservice.exceptions.CategoryAlreadyExistException;
import com.shopkart.productcatalogueservice.exceptions.CategoryOnDeleteException;
import com.shopkart.productcatalogueservice.exceptions.CategoryNotFoundException;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.State;
import com.shopkart.productcatalogueservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Profile("database")
public class SelfCategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public SelfCategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    @Override
    public Category createCategory(Category category) {
        boolean categoryExist=categoryRepository.existsByNameAndState(category.getName(),State.ACTIVE);
        if(categoryExist)
            throw new CategoryAlreadyExistException("Category is already exist with given categoryName");
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
        return from(categoryRepository.findByState(State.ACTIVE.name()));
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
        Optional<Category> categoryOptional=categoryRepository.findByIdAndStateAndProductsIsEmpty(categoryId,State.ACTIVE);
        if(categoryOptional.isEmpty())
            throw new CategoryOnDeleteException("Category could not be found or deleted...!");
        Category category=categoryOptional.get();
        category.setState(State.DELETED);
        category.setUpdatedAt(new Date());
        categoryRepository.save(category);
    }

    private Category from(CategoryIdNameDescriptionDbRecord categoryDbRecord){
        Category category=new Category();
        category.setId(categoryDbRecord.id());
        category.setName(categoryDbRecord.name());
        category.setDescription(categoryDbRecord.description());
        return category;
    }
    private List<Category> from(List<CategoryIdNameDescriptionDbRecord> categoryDbRecords){
        return categoryDbRecords.stream().map(this::from).toList();
    }

}
