package com.shopkart.productcatalogueservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity(name = "products")
public class Product extends BaseModel{
    private String name;
    private Double price;
    private String description;
    @ManyToOne
    private Category category;
    private String imageUrl;
}
