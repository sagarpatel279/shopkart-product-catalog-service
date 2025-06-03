package com.shopkart.productcatalogueservice.dtos.records.db;

public record CategoryFeaturedProductsDbRecord(Long categoryId, String categoryName, String categoryDescription,
                                               Long featureProductId, String featuredProductName,
                                               Long productId, String productName) {
}
