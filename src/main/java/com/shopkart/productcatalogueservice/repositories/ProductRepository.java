package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM products p JOIN FETCH p.category where p.state=:state")
    List<Product> findAllByState(@Param("state") State state);

    @Query("SELECT p FROM products p JOIN FETCH p.category where p.state=:state and p.category.name=:category")
    List<Product> findAllByStateAndCategory_Name(@Param("state")State state,@Param("category") String category);

    boolean existsByNameAndStateAndCategory_NameAndCategory_State(String name, State state, String category, State categoryState);

    Optional<Product> findByIdAndState(Long productId, State state);

    boolean existsByCategory_IdAndState(Long categoryId, State state);
}