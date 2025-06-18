package com.shopkart.productcatalogueservice.clients.fakestoreapi.records;

import java.io.Serializable;

public record FakeStoreProductResponseRecord(
        Long id,
        String title,
        Double price,
        String description,
        String category,
        String image,
        FakeStoreRatingResponseRecord rating) implements Serializable {}
