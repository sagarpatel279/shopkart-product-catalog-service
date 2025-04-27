package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.ProductRequestRecord;
import com.shopkart.productcatalogueservice.dtos.records.ProductResponseRecord;
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
    public ResponseEntity<?> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        if(products.isEmpty()){
            return ResponseEntity.ok("No records");
        }
        ApiResponse<List<ProductResponseRecord>> apiResponse =new ApiResponse<>(products.stream().map(ProductMapper::toProductResponseRecord).toList(),"Successfully data fetched",HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id")@NotNull(message = "ID must be required to delete")@Positive(message = "ID must be greater than 0") Long id){
        ApiResponse<ProductResponseRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductResponseRecord(productService.getProduct(id)),
                "Successfully data fetched",HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("")
//    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createProduct(@RequestBody @Validated(OnCreate.class) ProductRequestRecord requestRecord){
        ApiResponse<ProductResponseRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductResponseRecord(productService.createProduct(ProductMapper.toProduct(requestRecord))),
                "New Product is created successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") @NotNull(message = "ID must be required to delete")@Positive(message = "ID must be greater than 0") Long id,@RequestBody @Validated(OnUpdate.class) ProductRequestRecord requestRecord){
        ApiResponse<ProductResponseRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductResponseRecord(productService.updateProduct(id,ProductMapper.toProduct(requestRecord))),"Product has been updated successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceProduct(@PathVariable("id") @NotNull(message = "ID must be required to delete")@Positive(message = "ID must be greater than 0") Long id,@RequestBody @Validated(OnReplace.class) ProductRequestRecord requestRecord){
        ApiResponse<ProductResponseRecord> apiResponse =new ApiResponse<>(ProductMapper.toProductResponseRecord(productService.replaceProduct(id,ProductMapper.toProduct(requestRecord))),"Product has been replaced successfully",HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") @NotNull(message = "ID must be required to delete")@Positive(message = "ID must be greater than 0") Long id){
        productService.deleteProduct(id);
        Map<String,Object> response=new HashMap<>();
        response.put("message","Product has been deleted successfully");
        response.put("status",HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable("category")@NotBlank(message = "Category name must be required")@NotNull(message = "Category name must be required") String category){
        List<Product> products=productService.getAllProductsByCategory(category);
        if(products.isEmpty())
            return ResponseEntity.ok("No records");
        ApiResponse<List<ProductResponseRecord>> apiResponse=new ApiResponse<>(products.stream().map(ProductMapper::toProductResponseRecord).toList(),"Data have been fetched successfully.",HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
}
