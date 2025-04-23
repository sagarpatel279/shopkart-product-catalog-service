package com.shopkart.productcatalogueservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ratings")
public class Rating extends BaseModel{
    private Double rate;
    @ManyToOne
    private Product product;
}
