package com.shopkart.productcatalogueservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel{
    private String name;
    private Double price;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;
    private String imageUrl;
    @Transient
    private ProductRating productRating;
    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
}
