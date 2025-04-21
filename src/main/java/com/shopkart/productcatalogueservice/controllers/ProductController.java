package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.*;
import com.shopkart.productcatalogueservice.dtos.ResponseStatus;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllProducts(){
        GetAllProductResponseDTO responseDTO=new GetAllProductResponseDTO();
        responseDTO.setProductDTOs(productService.getAllProducts().stream().map(ProductMapper::toProductDTO).toList());
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id){
        GetProductResponseDTO responseDTO=new GetProductResponseDTO();
        responseDTO.setProductDTO(ProductMapper.toProductDTO(productService.getProduct(id)));
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        GetProductResponseDTO responseDTO=new GetProductResponseDTO();
        responseDTO.setProductDTO(ProductMapper.toProductDTO(productService.createProduct(ProductMapper.toProduct(requestDto))));
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        GetProductResponseDTO responseDTO=new GetProductResponseDTO();
        responseDTO.setProductDTO(ProductMapper.toProductDTO(productService.updateProduct(id,ProductMapper.toProduct(requestDto))));
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceProduct(@PathVariable("id") Long id,@RequestBody ProductDTO requestDto) throws FakeStoreAPIException {
        GetProductResponseDTO responseDTO=new GetProductResponseDTO();
        responseDTO.setProductDTO(ProductMapper.toProductDTO(productService.replaceProduct(id,ProductMapper.toProduct(requestDto))));
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        Map<String,String> response=new HashMap<>();
        if(productService.deleteProduct(id)){
            response.put("message","Product deleted successfully");
            response.put("status",ResponseStatus.SUCCESS.toString());
            return ResponseEntity.ok(response);
        }
        response.put("status",ResponseStatus.FAILURE.toString());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
