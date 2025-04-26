package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.CategoryMapper;
import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.CategoryRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.services.CategoryService;
import com.shopkart.productcatalogueservice.validations.groups.OnCreate;
import com.shopkart.productcatalogueservice.validations.groups.OnReplace;
import com.shopkart.productcatalogueservice.validations.groups.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("{id}")
    public ResponseEntity<?> getCategory(@NotNull(message = "ID must not be null")@Positive(message = "ID must be greater than zero") @PathVariable("id") Long id){
        ApiResponse<CategoryRecord> apiResponse=new ApiResponse<>(CategoryMapper.toCategoryRecord(categoryService.getCategory(id)),"Data have been fetched successfully",HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Validated(OnCreate.class) @RequestBody CategoryRecord categoryRecord){
        Category category=categoryService.createCategory(CategoryMapper.toCategory(categoryRecord));
        ApiResponse<CategoryRecord> apiResponse=new ApiResponse<>(CategoryMapper.toCategoryRecord(category),"Category has been created successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateCategory(@NotNull(message = "ID must not be null")@Positive(message = "ID must be greater than zero")@PathVariable("id") Long id,@Validated(OnUpdate.class)@RequestBody CategoryRecord categoryRecord){
        Category category =categoryService.updateCategory(id,CategoryMapper.toCategory(categoryRecord));
        ApiResponse<CategoryRecord> apiResponse=new ApiResponse<>(CategoryMapper.toCategoryRecord(category),"Category has been updated successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> replaceCategory(@NotNull(message = "ID must not be null")@Positive(message = "ID must be greater than zero")@PathVariable("id") Long id,@Validated(OnReplace.class)@RequestBody CategoryRecord categoryRecord){
        Category category =categoryService.replaceCategory(id,CategoryMapper.toCategory(categoryRecord));
        ApiResponse<CategoryRecord> apiResponse=new ApiResponse<>(CategoryMapper.toCategoryRecord(category),"Category has been replaced successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@NotNull(message = "ID must not be null")@Positive(message = "ID must be greater than zero")@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        Map<String,Object> response=new HashMap<>();
        response.put("message","Category has been deleted successfully");
        response.put("status",HttpStatus.OK);
        return ResponseEntity.ok(response);
    }
}
