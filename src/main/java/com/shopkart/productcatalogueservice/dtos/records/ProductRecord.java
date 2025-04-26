package com.shopkart.productcatalogueservice.dtos.records;

import com.shopkart.productcatalogueservice.validations.groups.*;
import jakarta.validation.constraints.*;

public record ProductRecord(
        @Null(groups = {OnUpdate.class, OnReplace.class, OnCreate.class},message = "ID must be null to apply CRUD on Product")
        Long id,
        @NotBlank(groups = {OnCreate.class,OnReplace.class},message = "Name must be required to create or replace a Product")
        String name,
        @NotNull(groups = {OnCreate.class,OnReplace.class},message = "Price must be required to create or replace a Product")
        @DecimalMin(groups = {OnCreate.class, OnUpdate.class, OnReplace.class},value = "0.0", inclusive = false,message="Price must be greater than 0")
        @DecimalMax(groups = {OnCreate.class, OnUpdate.class, OnReplace.class},value = "1.7976931348623157E308", message="Price is incorrect")
        Double price,
        @NotBlank(groups = {OnCreate.class,OnReplace.class},message = "Category name must be required to create or replace a Product")
        String description,
        @NotBlank(groups = {OnCreate.class,OnReplace.class},message = "Category name must be required to create  or replace a Product")
        String categoryName,
        @NotBlank(groups = {OnCreate.class,OnReplace.class},message = "Image URL must be provided to create  or replace a Product")
        String imageUrl){
//        ,RatingResponseRecord rating) {

//        public static record RatingResponseRecord(Double rate,Integer count){}
}
//
