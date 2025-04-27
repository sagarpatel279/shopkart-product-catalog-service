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

    @Query(value = """
                    SELECT EXISTS(SELECT 1 FROM products p where p.category_id=:categoryId AND p.state!=:state) OR EXISTS(SELECT 1 FROM categories_featured_products cfp JOIN products p2 ON p2.id=cfp.featured_products_id WHERE p2.state!=:state AND cfp.categories_id=:categoryId)
                    """,nativeQuery = true)
    boolean existsCategoryReferenceInNotDeletedChildEntity(@Param("categoryId") Long categoryId,@Param("state")State state);
}
