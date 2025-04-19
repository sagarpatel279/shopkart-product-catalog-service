package com.shopkart.productcatalogueservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String categoryName;
    private String imageUrl;
}
