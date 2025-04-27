package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.State;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
//    @Query("SELECT p FROM products p JOIN FETCH p.category where p.state=:state")

    @EntityGraph(attributePaths = {"category","ratings"})
    List<Product> findAllByState(State state);

//    @Query("SELECT p FROM products p JOIN FETCH p.category where p.state=:state and p.category.name=:category")
    @EntityGraph(attributePaths = {"category","ratings"})
    List<Product> findAllByStateAndCategory_Name(State state,String category);

    @EntityGraph(attributePaths = {"category","ratings"})
    boolean existsByNameAndStateAndCategory_NameAndCategory_State(String name, State state, String category, State categoryState);

    @EntityGraph(attributePaths = {"category","ratings"})
    Optional<Product> findByIdAndState(Long productId, State state);

    @EntityGraph(attributePaths = {"category","ratings"})
    boolean existsByCategory_IdAndState(Long categoryId, State state);
}