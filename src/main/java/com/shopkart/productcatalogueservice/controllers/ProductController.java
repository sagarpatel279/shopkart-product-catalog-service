package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.ProductDTO;
import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.dtos.ResponseStatus;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.ProductRecord;
import com.shopkart.productcatalogueservice.services.ProductService;
import com.shopkart.productcatalogueservice.validations.groups.OnCreate;
import com.shopkart.productcatalogueservice.validations.groups.OnReplace;
import com.shopkart.productcatalogueservice.validations.groups.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllProducts() throws FakeStoreAPIException {
        ApiResponse<List<ProductRecord>> apiResponse =new ApiResponse<>(productService.getAllProducts().stream().map(ProductMapper::toProductRecord).toList(),"Successfully data fetched",HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id")@NotNull(message = "ID must be required to delete") @Positive(message = "ID must be greater than 0") Long id) throws FakeStoreAPIException {
        ApiResponse<ProductRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductRecord(productService.getProduct(id)),
                "Successfully data fetched",HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody @Validated(OnCreate.class) ProductRecord requestRecord) throws FakeStoreAPIException {
        ApiResponse<ProductRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductRecord(productService.createProduct(ProductMapper.toProduct(requestRecord))),
                "New Product is created successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") @NotNull(message = "ID must be required to delete") @Positive(message = "ID must be greater than 0") Long id,@RequestBody @Validated(OnUpdate.class) ProductRecord requestRecord) throws FakeStoreAPIException {
        ApiResponse<ProductRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductRecord(productService.updateProduct(id,ProductMapper.toProduct(requestRecord))),"Product has been updated successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceProduct(@PathVariable("id") @NotNull(message = "ID must be required to delete") @Positive(message = "ID must be greater than 0") Long id,@RequestBody @Validated(OnReplace.class) ProductRecord requestRecord) throws FakeStoreAPIException {
        ApiResponse<ProductRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductRecord(productService.replaceProduct(id,ProductMapper.toProduct(requestRecord))),"Product has been replaced successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") @NotNull(message = "ID must be required to delete") @Positive(message = "ID must be greater than 0") Long id) throws FakeStoreAPIException {
        productService.deleteProduct(id);
        Map<String,Object> response=new HashMap<>();
        response.put("message","Product has been deleted successfully");
        response.put("status",HttpStatus.OK);
        return ResponseEntity.ok(response);
    }
}
