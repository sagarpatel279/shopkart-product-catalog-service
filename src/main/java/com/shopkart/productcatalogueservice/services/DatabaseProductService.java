package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseProductService implements ProductService{
    @Override
    public Product createProduct(Product product) throws FakeStoreAPIException {
        return null;
    }

    @Override
    public Product updateProduct(Long ProductId, Product product) throws FakeStoreAPIException {
        return null;
    }

    @Override
    public Product replaceProduct(Long ProductId, Product product) throws FakeStoreAPIException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() throws FakeStoreAPIException {
        return List.of();
    }

    @Override
    public Product getProduct(Long productId) throws FakeStoreAPIException {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) throws FakeStoreAPIException {

    }
}
