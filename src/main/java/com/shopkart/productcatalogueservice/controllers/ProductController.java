package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.ProductRequestRecord;
import com.shopkart.productcatalogueservice.dtos.records.ProductResponseRecord;
import com.shopkart.productcatalogueservice.exceptions.ProductNotFoundException;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.services.ProductService;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${shopkart.api.product-path}")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
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
            return ResponseEntity.ok(emptyResponse);
        }else if(products.isEmpty()){
            ApiResponse<List<ProductResponseRecord>> emptyResponse = new ApiResponse<>(
                    Collections.emptyList(),
                    "No records found",
                    HttpStatus.NO_CONTENT.value()
            );
            return ResponseEntity.ok(emptyResponse);
        }
        List<ProductResponseRecord> responseRecords = products.stream()
                .map(ProductMapper::toProductResponseRecord)
                .toList();

        ApiResponse<List<ProductResponseRecord>> apiResponse = new ApiResponse<>(
                responseRecords,
                "Successfully fetched data",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseRecord>> getProduct(
            @PathVariable("id")
            @NotNull(message = "ID must be required to delete")
            @Positive(message = "ID must be greater than 0") Long id) {

        ProductResponseRecord productResponse = ProductMapper.toProductWithRatingResponseRecord(productService.getProduct(id));

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                productResponse,
                "Successfully fetched data",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("")
//    @CrossOrigin(origins = "*")
    public ResponseEntity<ApiResponse<ProductResponseRecord>> createProduct(
            @RequestBody @Validated(OnCreate.class) ProductRequestRecord requestRecord) {

        Product createdProduct = productService.createProduct(ProductMapper.toProduct(requestRecord));
        ProductResponseRecord responseRecord = ProductMapper.toProductResponseRecord(createdProduct);

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                responseRecord,
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

        Product updatedProduct = productService.updateProduct(id, ProductMapper.toProduct(requestRecord));
        ProductResponseRecord responseRecord = ProductMapper.toProductResponseRecord(updatedProduct);

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                responseRecord,
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

        Product replacedProduct = productService.replaceProduct(id, ProductMapper.toProduct(requestRecord));
        ProductResponseRecord responseRecord = ProductMapper.toProductResponseRecord(replacedProduct);

        ApiResponse<ProductResponseRecord> apiResponse = new ApiResponse<>(
                responseRecord,
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
            @NotBlank(message = "Category name must be provided")
            String category) {

        List<Product> products = productService.getAllProductsByCategory(category);

        if (products.isEmpty()) {
            ApiResponse<List<ProductResponseRecord>> emptyResponse = new ApiResponse<>(
                    Collections.emptyList(),
                    "No records found for the given category",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(emptyResponse);
        }

        List<ProductResponseRecord> responseRecords = products.stream()
                .map(ProductMapper::toProductResponseRecord)
                .toList();

        ApiResponse<List<ProductResponseRecord>> apiResponse = new ApiResponse<>(
                responseRecords,
                "Data have been fetched successfully",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(apiResponse);
    }

}
