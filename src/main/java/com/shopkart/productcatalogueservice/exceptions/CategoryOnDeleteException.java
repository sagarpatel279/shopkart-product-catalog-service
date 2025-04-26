package com.shopkart.productcatalogueservice.exceptions;

public class CategoryOnDeleteException extends RuntimeException {
    public CategoryOnDeleteException(String message) {
        super(message);
    }
}
