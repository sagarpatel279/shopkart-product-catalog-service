package com.shopkart.productcatalogueservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
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
    private Category category;
    private String imageUrl;
    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
    @Transient
    private Integer count;
}
