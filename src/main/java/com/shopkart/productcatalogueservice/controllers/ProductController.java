package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.ProductRatingRecord;
import com.shopkart.productcatalogueservice.dtos.records.ProductRequestRecord;
import com.shopkart.productcatalogueservice.dtos.records.ProductResponseRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.ProductRating;
import com.shopkart.productcatalogueservice.services.IProductService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import com.shopkart.productcatalogueservice.validations.groups.OnCreate;
import com.shopkart.productcatalogueservice.validations.groups.OnReplace;
import com.shopkart.productcatalogueservice.validations.groups.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("${shopkart.api.product-path}")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService){
        this.productService=productService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ProductResponseRecord>>> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        if(products==null){
            ApiResponse<List<ProductResponseRecord>> emptyResponse = new ApiResponse<>(
                    null,
                    "Error retrieving products",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return new ResponseEntity<>(emptyResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(products.isEmpty()){
            ApiResponse<List<ProductResponseRecord>> emptyResponse = new ApiResponse<>(
                    Collections.emptyList(),
                    "No products found",
                    HttpStatus.NO_CONTENT.value()
            );
            return new ResponseEntity<>(emptyResponse,HttpStatus.NO_CONTENT);
        }

        ApiResponse<List<ProductResponseRecord>> apiResponse = new ApiResponse<>(
                from(products),
                "Products retrieved successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseRecord>> getProduct(
            @PathVariable("id")
            @NotNull(message = "ID must be required to delete")
            @Positive(message = "ID must be greater than 0") Long id) {

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                from(productService.getProduct(id)),
                "Products retrieved successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("")
//    @CrossOrigin(origins = "*")
    public ResponseEntity<ApiResponse<ProductResponseRecord>> createProduct(
            @RequestBody @Validated(OnCreate.class) ProductRequestRecord requestRecord) {

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                from(productService.createProduct(from(requestRecord))),
                "New Product is created successfully",
                HttpStatus.CREATED.value()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseRecord>> updateProduct(
            @PathVariable("id")
            @NotNull(message = "ID must be required to update")
            @Positive(message = "ID must be greater than 0") Long id,

            @RequestBody @Validated(OnUpdate.class) ProductRequestRecord requestRecord) {

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                from(productService.updateProduct(id,from(requestRecord))),
                "Product has been updated successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseRecord>> replaceProduct(
            @PathVariable("id")
            @NotNull(message = "ID must be required to replace")
            @Positive(message = "ID must be greater than 0") Long id,

            @RequestBody
            @Validated(OnReplace.class) ProductRequestRecord requestRecord) {

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                from(productService.replaceProduct(id,from(requestRecord))),
                "Product has been replaced successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable("id")
            @NotNull(message = "ID must be required to delete")
            @Positive(message = "ID must be greater than 0") Long id) {

        productService.deleteProduct(id);

        ApiResponse<Void> apiResponse = new ApiResponse<>(
                null,
                "Product has been deleted successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductResponseRecord>>> getAllProductsByCategory(
            @PathVariable("category")
            @NotBlank(message = "Category productName must be provided")
            String category) {

        List<Product> products = productService.getAllProductsByCategory(category);

        if (products.isEmpty()) {
            ApiResponse<List<ProductResponseRecord>> emptyResponse = new ApiResponse<>(
                    Collections.emptyList(),
                    "No records found for the given categoryName",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(emptyResponse);
        }
        ApiResponse<List<ProductResponseRecord>> apiResponse = new ApiResponse<>(
                from(products),
                "Data have been fetched successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }
    private Product from(ProductRequestRecord productRequestRecord){
        Product product=new Product();
        product.setId(productRequestRecord.id());
        product.setName(productRequestRecord.productName());
        product.setPrice(productRequestRecord.price());
        product.setDescription(productRequestRecord.description());
        product.setImageUrl(productRequestRecord.imageUrl());
        Category category=new Category();
        category.setName(productRequestRecord.categoryName());
        product.setCategory(category);
        return product;
    }
    private ProductResponseRecord from(Product product){
        ProductRating productRating=product.getProductRating();
        return new ProductResponseRecord(product.getId(),product.getName(),
                product.getPrice(),product.getDescription(),
                product.getCategory().getName(),product.getImageUrl(),productRating!=null?new ProductRatingRecord(productRating.getAverageRate(),productRating.getReviewCount()):null);
    }
    private List<ProductResponseRecord> from(List<Product> products){
        List<ProductResponseRecord> productResponseRecord=new ArrayList<>();
        for(Product product:products){
            productResponseRecord.add(from(product));
        }
        return productResponseRecord;
    }
}
