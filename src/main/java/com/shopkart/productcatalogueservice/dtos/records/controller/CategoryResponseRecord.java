package com.shopkart.productcatalogueservice.dtos.records.controller;

import java.util.List;

public record CategoryResponseRecord(Long id, String categoryName, String description,
                                     List<CategoryProductsRecord> featuredProducts,List<CategoryProductsRecord> categoryProducts) {
}
