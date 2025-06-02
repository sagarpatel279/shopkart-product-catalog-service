package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.State;
import com.shopkart.productcatalogueservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SelfCategoryServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    void testNPlusOneProblem(){
        List<Product> products=productRepository.findAllByState(State.ACTIVE);
        System.out.println("=========================Products are fetched=================");
        for(Product product:products){
            System.out.print("Product Name: "+product.getName()+", ");
        }
        System.out.println("\n=================Now Fetching Category=========================");
        for(Product product:products){
            System.out.print("Category Name: "+product.getCategory().getName()+", ");
        }
    }
}