package com.shopkart.productcatalogueservice.clients.fakestoreapi.records;

import java.io.Serializable;

public record FakeStoreRatingResponseRecord (
        Double rate,
        Integer count) implements Serializable {}
