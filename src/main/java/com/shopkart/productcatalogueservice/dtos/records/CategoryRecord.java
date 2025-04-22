package com.shopkart.productcatalogueservice.dtos.records;

import com.shopkart.productcatalogueservice.validations.groups.OnCreate;
import com.shopkart.productcatalogueservice.validations.groups.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.hibernate.annotations.OnDelete;

public record CategoryRecord(
        @Null(groups = {OnCreate.class, OnUpdate.class, OnDelete.class},message = "ID must be null when apply CRUD on category")
        Long id,
        @NotNull(groups = OnCreate.class,message = "Name must be required to create category")
        String name,
        String description) {
}
