package com.shopkart.productcatalogueservice.exceptions;

public class ProductExistWithCategoryException extends RuntimeException {
    public ProductExistWithCategoryException(String message) {
        super(message);
    }
}
