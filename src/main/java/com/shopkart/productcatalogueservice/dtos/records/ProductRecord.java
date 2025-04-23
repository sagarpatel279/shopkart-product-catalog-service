package com.shopkart.productcatalogueservice.dtos.records;

import com.shopkart.productcatalogueservice.validations.groups.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

public record ProductRecord(
        @Null(groups = {OnUpdate.class, OnReplace.class, OnCreate.class},message = "ID must be null to apply CRUD on Product")
        Long id,
        @NotBlank(groups = OnCreate.class,message = "Name must be required to create a Product")
        String name,
        @NotNull(groups = OnCreate.class,message = "Price must be required to create a Product")
        @Positive(message = "Price must be greater than 0")
        Double price,
        @NotBlank(groups = OnCreate.class,message = "Category name must be required to create a Product")
        String description,
        @NotBlank(groups = OnCreate.class,message = "Category name must be required to create a Product")
        String categoryName,
        @NotBlank(groups = OnCreate.class,message = "Image URL must be provided to create a Product")
        String imageUrl,
        RatingResponseRecord rating) {

        public static record RatingResponseRecord(Double rate,Integer count){}
}
