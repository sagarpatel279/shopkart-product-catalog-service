package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByNameAndCategory_Name(String name, String categoryName);
    @Query("SELECT p FROM products p JOIN FETCH p.category where p.state=:state")
    List<Product> findAllByState(@Param("state") State state);
}