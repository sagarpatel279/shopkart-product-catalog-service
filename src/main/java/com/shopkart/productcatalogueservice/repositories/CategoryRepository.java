package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.State;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

//    @EntityGraph(attributePaths = {"products"})
    Optional<Category> findByNameAndState(String name,State state);

    List<Category> findAllByState(State state);

    boolean existsByNameAndState(String category, State state);

    boolean existsByIdAndState(Long categoryId, State state);

    Optional<Category> findByIdAndState(Long categoryId, State state);


    Optional<Category> findByIdAndStateAndProductsIsEmpty(Long categoryId,State state);
}
