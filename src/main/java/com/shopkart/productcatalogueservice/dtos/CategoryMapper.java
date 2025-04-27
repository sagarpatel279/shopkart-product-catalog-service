package com.shopkart.productcatalogueservice.dtos;

import com.shopkart.productcatalogueservice.dtos.records.CategoryProductsRecord;
import com.shopkart.productcatalogueservice.dtos.records.CategoryRequestRecord;
import com.shopkart.productcatalogueservice.dtos.records.CategoryResponseRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;

import java.util.List;

public class CategoryMapper {
    public static Category toCategory(CategoryRequestRecord categoryRecord){
        Category category=new Category();
        category.setId(categoryRecord.id());
        category.setName(categoryRecord.categoryName());
        category.setDescription(categoryRecord.description());
        return category;
    }
    public static CategoryRequestRecord toCategoryRequestRecord(Category category){
        return new CategoryRequestRecord(category.getId(), category.getName(), category.getDescription());
    }
    private static CategoryProductsRecord toCategoryProductRecord(Product product){
        return new CategoryProductsRecord(product.getId(),product.getName());
    }
    public static CategoryResponseRecord toCategoryResponseRecord(Category category){
        return new CategoryResponseRecord(category.getId(),category.getName(), category.getDescription(), null,null);
    }
    public static CategoryResponseRecord toCategoryAndCategoryProductsResponseRecord(Category category){
        List<CategoryProductsRecord> featureProducts=category.getFeaturedProducts()!=null?category.getFeaturedProducts().stream().map(CategoryMapper::toCategoryProductRecord).toList():List.of();
        List<CategoryProductsRecord> allProducts=category.getAllProducts()!=null?category.getAllProducts().stream().map(CategoryMapper::toCategoryProductRecord).toList():List.of();
        return new CategoryResponseRecord(category.getId(),category.getName(), category.getDescription(), featureProducts,allProducts);
    }
}
