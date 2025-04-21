package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product) throws FakeStoreAPIException;
    Product updateProduct(Long ProductId, Product product) throws FakeStoreAPIException;
    Product replaceProduct(Long ProductId, Product product) throws FakeStoreAPIException;
    List<Product> getAllProducts();
    Product getProduct(Long productId);
    Boolean deleteProduct(Long productId);
}
