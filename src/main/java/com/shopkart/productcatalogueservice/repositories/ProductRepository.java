package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
