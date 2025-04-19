package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;

public interface ProductService {
    Product createProduct(Product product) throws FakeStoreAPIException;
}
