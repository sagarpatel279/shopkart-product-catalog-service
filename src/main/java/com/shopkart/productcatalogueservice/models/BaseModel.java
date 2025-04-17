package com.shopkart.productcatalogueservice.models;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    private State state;
}
