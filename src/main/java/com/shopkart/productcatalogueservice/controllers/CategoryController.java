package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.CategoryMapper;
import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.CategoryRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.repositories.ProductRepository;
import com.shopkart.productcatalogueservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${shopkart.api.product-category-path}")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllCategories(){
        List<Category> categories=categoryService.getAllCategory();
        if(categories.isEmpty())
            return ResponseEntity.ok("No Category found..");
        ApiResponse<List<CategoryRecord>> apiResponse=new ApiResponse<>(categories.stream().map(CategoryMapper::toCategoryRecord).toList(),"All Categories have been fetched", HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
    public ResponseEntity<?> getCategory(Long id){
        return null;
    }

    public ResponseEntity<?> createCategory(){
        return null;
    }
    public ResponseEntity<?> updateCategory(){
        return null;
    }
    public ResponseEntity<?> replaceCategory(){
        return null;
    }
    public ResponseEntity<?> deleteCategory(){
        return null;
    }
}
