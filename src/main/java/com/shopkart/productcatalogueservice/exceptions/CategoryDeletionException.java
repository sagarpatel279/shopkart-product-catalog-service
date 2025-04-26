package com.shopkart.productcatalogueservice.exceptions;

public class CategoryDeletionException extends RuntimeException {
    public CategoryDeletionException(String message) {
        super(message);
    }
}
