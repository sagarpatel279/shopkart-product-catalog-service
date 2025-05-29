package com.shopkart.productcatalogueservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel{
    private String name;
    private Double price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private String imageUrl;
    @Transient
    private ProductRating productRating;
    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
}
