package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.State;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
//    @Query("SELECT p FROM products p JOIN FETCH p.categoryName where p.state=:state")

    @EntityGraph(attributePaths = {"categoryName","ratings"})
    List<Product> findAllByState(State state);

//    @Query("SELECT p FROM products p JOIN FETCH p.categoryName where p.state=:state and p.categoryName.categoryName=:categoryName")
    @EntityGraph(attributePaths = {"categoryName","ratings"})
    List<Product> findAllByStateAndCategory_Name(State state,String category);

    @EntityGraph(attributePaths = {"categoryName","ratings"})
    boolean existsByNameAndStateAndCategory_NameAndCategory_State(String name, State state, String category, State categoryState);

    @EntityGraph(attributePaths = {"categoryName","ratings"})
    Optional<Product> findByIdAndState(Long productId, State state);

//    @EntityGraph(attributePaths = {"categoryName","ratings"})
//    boolean existsByCategory_IdAndState(Long categoryId, State state);
}