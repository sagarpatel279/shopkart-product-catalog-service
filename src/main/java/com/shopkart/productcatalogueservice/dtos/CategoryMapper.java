package com.shopkart.productcatalogueservice.dtos;

import com.shopkart.productcatalogueservice.dtos.records.CategoryRecord;
import com.shopkart.productcatalogueservice.models.Category;

public class CategoryMapper {
    public static Category toCategory(CategoryRecord categoryRecord){
        Category category=new Category();
        category.setId(categoryRecord.id());
        category.setName(categoryRecord.name());
        category.setDescription(categoryRecord.description());
        return category;
    }
    public static CategoryRecord toCategoryRecord(Category category){
        return new CategoryRecord(category.getId(), category.getName(), category.getDescription());
    }
}
