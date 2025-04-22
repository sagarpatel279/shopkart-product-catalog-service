package com.shopkart.productcatalogueservice.dtos.records;

import jakarta.validation.constraints.NotNull;

public record RateRecord(
        @NotNull(message = "Rating must be required")
        Integer ratings) {
}
