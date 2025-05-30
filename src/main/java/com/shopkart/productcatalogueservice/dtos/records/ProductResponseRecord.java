package com.shopkart.productcatalogueservice.dtos.records;

public record ProductResponseRecord(Long id
        , String productName, Double price, String description
        , String categoryName, String imageUrl, ProductRatingRecord rating) {
}
