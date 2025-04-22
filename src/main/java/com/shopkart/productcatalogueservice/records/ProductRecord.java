package com.shopkart.productcatalogueservice.records;

import com.shopkart.productcatalogueservice.validations.groups.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

public record ProductRecord(
        @Null(groups = {OnUpdate.class, OnReplace.class, OnCreate.class},message = "ID must be null to apply CRUD on Product")
        Long id,
        @NotNull(groups = OnCreate.class,message = "Name must be required to create a Product")
        String name,
        @NotNull(groups = OnCreate.class,message = "Price must be required to create a Product")
        @Positive(message = "Price must be greater than 0")
        Double price,

        String description,
        @NotNull(groups = OnCreate.class,message = "Category name must be required to create a Product")
        String categoryName,
        @NotNull(groups = OnCreate.class,message = "Image URL must be provided to create a Product")
        String imageUrl) {
}
