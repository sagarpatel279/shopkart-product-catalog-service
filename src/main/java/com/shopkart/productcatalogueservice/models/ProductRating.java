package com.shopkart.productcatalogueservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRating {
    private Double averageRate;
    private Integer reviewCount;
}
