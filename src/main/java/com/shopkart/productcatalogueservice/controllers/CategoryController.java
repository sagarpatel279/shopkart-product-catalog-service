package com.shopkart.productcatalogueservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    public ResponseEntity<?> getAllCategories(){
        return null;
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
