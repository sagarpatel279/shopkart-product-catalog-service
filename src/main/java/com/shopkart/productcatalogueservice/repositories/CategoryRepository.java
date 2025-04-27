package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.State;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @EntityGraph(attributePaths = {"allProducts"})
    Optional<Category> findByNameAndState(String name,State state);

//    @Query("SELECT c from categories  c where c.state=:state")
    List<Category> findAllByState(State state);

    @EntityGraph(attributePaths = {"allProducts"})
    boolean existsByNameAndState(String category, State state);

    @EntityGraph(attributePaths = {"allProducts"})
    boolean existsByIdAndState(Long categoryId, State state);

    @EntityGraph(attributePaths = {"allProducts"})
    Optional<Category> findByIdAndState(Long categoryId, State state);
}
