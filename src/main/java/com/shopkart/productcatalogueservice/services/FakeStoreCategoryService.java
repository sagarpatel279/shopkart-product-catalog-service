package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.clients.fakestoreapi.FakeStoreApiCategoryClient;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreCategoryRecord;
import com.shopkart.productcatalogueservice.models.Category;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("fakestore")
public class FakeStoreCategoryService implements CategoryService{

    private FakeStoreApiCategoryClient fakeStoreApiCategoryClient;
    public FakeStoreCategoryService(FakeStoreApiCategoryClient fakeStoreApiCategoryClient){
        this.fakeStoreApiCategoryClient=fakeStoreApiCategoryClient;
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
        return from(fakeStoreApiCategoryClient.getAllFakeStoreCategories());
    }

    @Override
    public Category getCategory(Long categoryId) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }

    private Category from(String categoryName){
        Category category=new Category();
        category.setName(categoryName);
        return category;
    }
    private List<Category> from(String[] categories){
        return Arrays.stream(categories).map(this::from).toList();
    }
}
