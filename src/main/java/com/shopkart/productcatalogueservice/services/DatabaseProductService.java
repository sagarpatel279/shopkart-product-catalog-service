package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.repositories.CategoryRepository;
import com.shopkart.productcatalogueservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("database")
public class DatabaseProductService implements ProductService{
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public DatabaseProductService(CategoryRepository categoryRepository,ProductRepository productRepository){
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
    }
    @Override
    public Product createProduct(Product product) {
        Optional<Category> categoryOptional=categoryRepository.findByName(product.getCategory().getName());
        Category category;
        category = categoryOptional.orElseGet(() -> categoryRepository.save(product.getCategory()));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long ProductId, Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long ProductId, Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(Long productId) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }
}
