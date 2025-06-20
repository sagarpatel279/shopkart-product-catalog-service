package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.exceptions.CategoryNotFoundException;
import com.shopkart.productcatalogueservice.exceptions.ProductExistWithCategoryException;
import com.shopkart.productcatalogueservice.exceptions.ProductNotFoundException;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.State;
import com.shopkart.productcatalogueservice.repositories.CategoryRepository;
import com.shopkart.productcatalogueservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Profile("database")
public class SelfProductService implements IProductService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    @Autowired
    public SelfProductService(CategoryRepository categoryRepository, ProductRepository productRepository){
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
    }
    @Override
    public Product createProduct(Product product) {
        Category category=createOrUpdateAsPerCategoryExist(product.getCategory());
        boolean exist=productRepository.existsByNameAndStateAndCategory_NameAndCategory_State(product.getName(),State.ACTIVE,category.getName(),State.ACTIVE);
        if(exist){
            throw new ProductExistWithCategoryException("Product already exists in given categoryName");
        }
        product.setCategory(category);
        return productRepository.save(product);
    }
    private Category createOrUpdateAsPerCategoryExist(Category category){
        Optional<Category> categoryOptional=categoryRepository.findByNameAndState(category.getName(),State.ACTIVE);
        return categoryOptional.orElseGet(() -> categoryRepository.save(category));
    }
    @Override
    public Product updateProduct(Long productId, Product product) {
        Product productFromDb = productRepository.findByIdAndState(productId,State.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException("Product could not be found by given product Id"));

        if(product.getName()!=null && !product.getName().isBlank())
            productFromDb.setName(product.getName());
        if(product.getPrice()!=null && !Double.isNaN(product.getPrice()))
            productFromDb.setPrice(product.getPrice());
        if(product.getDescription()!=null && !product.getDescription().isBlank())
            productFromDb.setDescription(product.getDescription());
        if(product.getImageUrl()!=null && !product.getImageUrl().isBlank())
            productFromDb.setImageUrl(product.getImageUrl());
        if(product.getCategory()!=null && product.getCategory().getName()!=null && !product.getCategory().getName().isBlank()){
            productFromDb.setCategory(createOrUpdateAsPerCategoryExist(product.getCategory()));
        }
        productFromDb.setUpdatedAt(new Date());
        return productRepository.save(productFromDb);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        Product productFromDb = productRepository.findByIdAndState(productId,State.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException("Product could not be found by given product Id"));
        productFromDb.setName(product.getName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setImageUrl(product.getImageUrl());
        productFromDb.setUpdatedAt(new Date());
        productFromDb.setCategory(createOrUpdateAsPerCategoryExist(product.getCategory()));
        return productRepository.save(productFromDb);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllByState(State.ACTIVE);
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findByIdAndState(productId,State.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException("Product could not be found by given product Id"));
    }

    @Override
    public void deleteProduct(Long productId) {
        Product productFromDb = productRepository.findByIdAndState(productId,State.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException("Product could not be found by given product Id"));

        productFromDb.setState(State.DELETED);
        productFromDb.setUpdatedAt(new Date());
        productRepository.save(productFromDb);
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        category=slugToCategoryName(category);
        boolean categoryExist=categoryRepository.existsByNameAndState(category,State.ACTIVE);
        if(!categoryExist)
            throw new CategoryNotFoundException("Category could not be found by given categoryName categoryName");
        return productRepository.findAllByStateAndCategory_Name(State.ACTIVE,category);
    }

    @Override
    public Page<Product> searchProductsInProductTitle(String query, int numberOfResults, int offset, String sortBy) {
        Pair<String,String> sortByPair=getSortFieldAndDirection(sortBy);
        Sort sort=sortByPair.getSecond().equals("asc")?
                Sort.by(sortByPair.getFirst()).ascending():
                Sort.by(sortByPair.getFirst()).descending();
        return productRepository.findAllByNameContaining(query,PageRequest.of((offset/numberOfResults),numberOfResults,sort));
    }

    private String slugToCategoryName(String slug){
        if(slug == null || slug.isBlank()){
            throw new IllegalArgumentException("Category slug can not be null or empty");
        }
        return slug.replace("-"," ");
    }

    private Pair<String,String> getSortFieldAndDirection(String sortBy){
        if(sortBy==null || sortBy.isBlank() || !sortBy.contains("_")){
            throw new IllegalArgumentException("Category slug can not be null or empty");
        }
        String[] strArr=sortBy.split("_");
        return Pair.of(strArr[0],strArr[1].equalsIgnoreCase("dsc")?"dsc":"asc");
    }
}
