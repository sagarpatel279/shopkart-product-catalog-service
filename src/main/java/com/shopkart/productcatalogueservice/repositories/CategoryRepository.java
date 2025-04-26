package com.shopkart.productcatalogueservice.repositories;

import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    @Query("SELECT c from categories  c where c.state=:state")
    List<Category> findAllByState(@Param("state") State state);
}
