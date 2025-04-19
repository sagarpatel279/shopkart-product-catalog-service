package com.shopkart.productcatalogueservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreAPIProductDTO {
    private Integer id;
    private String title;
    private Float price;
    private String description;
    private String category;
    private String image;
}
