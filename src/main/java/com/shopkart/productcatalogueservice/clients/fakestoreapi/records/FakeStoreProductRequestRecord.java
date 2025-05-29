package com.shopkart.productcatalogueservice.clients.fakestoreapi.records;

public record FakeStoreProductRequestRecord(Long id,
                                            String title,
                                            Double price,
                                            String description,
                                            String category,
                                            String image) {
}
