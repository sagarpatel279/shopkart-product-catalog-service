package com.shopkart.productcatalogueservice.dtos.records;

import com.shopkart.productcatalogueservice.validations.groups.OnCreate;
import com.shopkart.productcatalogueservice.validations.groups.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.hibernate.annotations.OnDelete;

public record CategoryRequestRecord(
        @Null(groups = {OnCreate.class, OnUpdate.class, OnDelete.class},message = "ID must be null")
        Long id,
        @NotNull(groups = {OnCreate.class, OnUpdate.class},message = "Name must be required to create or update categoryName")
        String categoryName,
        String description) {
}
