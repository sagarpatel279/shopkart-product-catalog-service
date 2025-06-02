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

    @EntityGraph(attributePaths = {"category"})
    List<Product> findAllByState(State state);

    @EntityGraph(attributePaths = {"category"})
    List<Product> findAllByStateAndCategory_Name(State state,String category);

    boolean existsByNameAndStateAndCategory_NameAndCategory_State(String name, State state, String category, State categoryState);

    @EntityGraph(attributePaths = {"category","ratings"})
    Optional<Product> findByIdAndState(Long productId, State state);

}