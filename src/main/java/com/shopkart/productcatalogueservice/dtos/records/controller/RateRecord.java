package com.shopkart.productcatalogueservice.dtos.records.controller;

import com.shopkart.productcatalogueservice.validations.groups.OnCreate;
import com.shopkart.productcatalogueservice.validations.groups.OnReplace;
import com.shopkart.productcatalogueservice.validations.groups.OnUpdate;
import jakarta.validation.constraints.*;

public record RateRecord(
        @NotNull(message = "Rating must be required")
        @DecimalMin(groups = {OnCreate.class, OnUpdate.class, OnReplace.class},value = "0.0", inclusive = true,message="Rating must be greater than 0")
        @DecimalMax(groups = {OnCreate.class, OnUpdate.class, OnReplace.class},value = "5.0", inclusive = true,message="Rating must be less than 5")
        Double ratings,
        String review
        ) {
}
