package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long ProductId, Product product);
    Product replaceProduct(Long ProductId, Product product);
    List<Product> getAllProducts();
    Product getProduct(Long productId);
    void deleteProduct(Long productId);
}
