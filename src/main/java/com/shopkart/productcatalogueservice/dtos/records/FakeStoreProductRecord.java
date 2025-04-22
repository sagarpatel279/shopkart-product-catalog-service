package com.shopkart.productcatalogueservice.dtos.records;

public record FakeStoreProductRecord(
        Integer id,
        String title,
        Float price,
        String description,
        String category,
        String image,
        FakeStoreRatingRecord ratings
) {
    public record FakeStoreRatingRecord(
            Double rate,
            Integer count
    ){

    }
}
