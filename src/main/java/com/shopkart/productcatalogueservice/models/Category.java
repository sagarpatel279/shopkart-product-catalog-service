package com.shopkart.productcatalogueservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "categories")
public class Category extends BaseModel{
    @Column(unique = true,nullable = false)
    private String name;
    private String description;
    @OneToMany
    private List<Product> featuredProducts;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(String categoryName, String description) {
        this.name=categoryName;
        this.description=description;
    }

    public Category(String categoryName) {
        this.name=categoryName;
        this.description=null;
    }
}
