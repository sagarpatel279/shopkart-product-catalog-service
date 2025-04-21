package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.GetProductResponseDTO;
import com.shopkart.productcatalogueservice.dtos.ProductDTO;
import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.dtos.ResponseStatus;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllProducts(){

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id){
        return null;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        GetProductResponseDTO responseDTO=new GetProductResponseDTO();
        responseDTO.setProductDTO(ProductMapper.toProductDTO(productService.createProduct(ProductMapper.toProduct(requestDto))));
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        GetProductResponseDTO responseDTO=new GetProductResponseDTO();
        responseDTO.setProductDTO(ProductMapper.toProductDTO(productService.updateProduct(id,ProductMapper.toProduct(requestDto))));
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceProduct(@PathVariable("id") Long id,@RequestBody ProductDTO requestDto){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        return null;
    }
}
