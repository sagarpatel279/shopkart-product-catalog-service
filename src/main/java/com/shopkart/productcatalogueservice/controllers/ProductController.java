package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.*;
import com.shopkart.productcatalogueservice.dtos.ResponseStatus;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllProducts() throws FakeStoreAPIException {
        ApiResponseWithData<List<ProductDTO>> apiResponse =new ApiResponseWithData<>();
        apiResponse.setData(productService.getAllProducts().stream().map(ProductMapper::toProductDTO).toList());
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) throws FakeStoreAPIException {
        ApiResponseWithData<ProductDTO> apiResponse =new ApiResponseWithData<>();
        apiResponse.setData(ProductMapper.toProductDTO(productService.getProduct(id)));
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        ApiResponseWithData<ProductDTO> apiResponse =new ApiResponseWithData<>();
        apiResponse.setData(ProductMapper.toProductDTO(productService.createProduct(ProductMapper.toProduct(requestDto))));
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        ApiResponseWithData<ProductDTO> apiResponse =new ApiResponseWithData<>();
        apiResponse.setData(ProductMapper.toProductDTO(productService.updateProduct(id,ProductMapper.toProduct(requestDto))));
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceProduct(@PathVariable("id") Long id,@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        ApiResponseWithData<ProductDTO> apiResponse =new ApiResponseWithData<>();
        apiResponse.setData(ProductMapper.toProductDTO(productService.replaceProduct(id,ProductMapper.toProduct(requestDto))));
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) throws FakeStoreAPIException {
        ApiResponse apiResponse=new ApiResponse();
        productService.deleteProduct(id);
        apiResponse.setMessage("Product deleted successfully");
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(apiResponse);
    }
}
