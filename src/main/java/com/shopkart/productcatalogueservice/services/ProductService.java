package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.models.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long productId, Product product);
    Product replaceProduct(Long productId, Product product);
    List<Product> getAllProducts();
    Product getProduct(Long productId);
    void deleteProduct(Long productId);
    List<Product> getAllProductsByCategory(String category);
}
