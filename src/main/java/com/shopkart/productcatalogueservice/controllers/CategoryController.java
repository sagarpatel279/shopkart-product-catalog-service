package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.CategoryMapper;
import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.CategoryRequestRecord;
import com.shopkart.productcatalogueservice.dtos.records.CategoryResponseRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.services.ICategoryService;
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

import java.util.List;

@RestController
@RequestMapping("${shopkart.api.product-category-path}")
public class CategoryController {
    private ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService){
        this.categoryService=categoryService;
    }


    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CategoryResponseRecord>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();

        List<CategoryResponseRecord> responseRecords = categories.stream()
                .map(CategoryMapper::toCategoryResponseRecord)
                .toList();

        ApiResponse<List<CategoryResponseRecord>> apiResponse = new ApiResponse<>(
                responseRecords,
                categories.isEmpty() ? "No categories found." : "All categories have been fetched.",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryResponseRecord>> getCategory(
            @PathVariable("id")
            @NotNull(message = "ID must not be null")
            @Positive(message = "ID must be greater than zero") Long id) {

        Category category = categoryService.getCategory(id);
        CategoryResponseRecord responseRecord = CategoryMapper.toCategoryAndCategoryProductsResponseRecord(category);

        ApiResponse<CategoryResponseRecord> apiResponse = new ApiResponse<>(
                responseRecord,
                "Data has been fetched successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CategoryResponseRecord>> createCategory(
            @Validated(OnCreate.class)
            @RequestBody CategoryRequestRecord categoryRecord) {

        Category category = categoryService.createCategory(CategoryMapper.toCategory(categoryRecord));

        ApiResponse<CategoryResponseRecord> apiResponse = new ApiResponse<>(
                CategoryMapper.toCategoryResponseRecord(category),
                "Category has been created successfully",
                HttpStatus.CREATED.value()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryResponseRecord>> updateCategory(
            @NotNull(message = "ID must not be null")
            @Positive(message = "ID must be greater than zero")
            @PathVariable("id") Long id,

            @Validated(OnUpdate.class)
            @RequestBody CategoryRequestRecord categoryRecord) {

        Category category = categoryService.updateCategory(id, CategoryMapper.toCategory(categoryRecord));

        ApiResponse<CategoryResponseRecord> apiResponse = new ApiResponse<>(
                CategoryMapper.toCategoryResponseRecord(category),
                "Category has been updated successfully",
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryResponseRecord>> replaceCategory(
            @NotNull(message = "ID must not be null")
            @Positive(message = "ID must be greater than zero")
            @PathVariable("id") Long id,

            @Validated(OnReplace.class)
            @RequestBody CategoryRequestRecord categoryRecord) {

        Category category = categoryService.replaceCategory(id, CategoryMapper.toCategory(categoryRecord));
        ApiResponse<CategoryResponseRecord> apiResponse = new ApiResponse<>(
                CategoryMapper.toCategoryResponseRecord(category),
                "Category has been replaced successfully",
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @NotNull(message = "ID must not be null")
            @Positive(message = "ID must be greater than zero")
            @PathVariable("id") Long id) {

        categoryService.deleteCategory(id);

        ApiResponse<Void> apiResponse = new ApiResponse<>(
                null,
                "Category has been deleted successfully",
                HttpStatus.NO_CONTENT.value()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

}
