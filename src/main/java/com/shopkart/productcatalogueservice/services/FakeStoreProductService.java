package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.clients.fakestoreapi.FakeStoreApiClient;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreProductRequestRecord;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreProductResponseRecord;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreRatingResponseRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.ProductRating;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("fakestore")
public class FakeStoreProductService implements ProductService{

    private FakeStoreApiClient fakeStoreApiClient;
    public FakeStoreProductService(FakeStoreApiClient fakeStoreApiClient){
        this.fakeStoreApiClient=fakeStoreApiClient;
    }

    @Override
    public Product createProduct(Product product) {
        return from(fakeStoreApiClient.createFakeStoreProduct(from(product)));
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return from(fakeStoreApiClient.updateFakeStoreProduct(productId,from(product)));
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        return from(fakeStoreApiClient.replaceFakeStoreProduct(productId,from(product)));
    }

    @Override
    public List<Product> getAllProducts() {
        return from(fakeStoreApiClient.getAllFakeStoreProducts());
    }

    @Override
    public Product getProduct(Long productId) {
        return from(fakeStoreApiClient.getSingleFakeStoreProduct(productId));
    }

    @Override
    public void deleteProduct(Long productId) {
        fakeStoreApiClient.deleteFakeStoreProduct(productId);
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return from(fakeStoreApiClient.getProductsByCategory(category));
    }

    private List<Product> from(FakeStoreProductResponseRecord[] fakeStoreProductResponseRecords){
        List<Product> products=new ArrayList<>();
        for(FakeStoreProductResponseRecord responseRecord:fakeStoreProductResponseRecords){
            products.add(from(responseRecord));
        }
        return products;
    }
    private Product from(FakeStoreProductResponseRecord fakeStoreProductResponseRecord){
        Product product=new Product();
        product.setId(fakeStoreProductResponseRecord.id());
        product.setPrice(fakeStoreProductResponseRecord.price());
        product.setName(fakeStoreProductResponseRecord.title());
        product.setDescription(fakeStoreProductResponseRecord.description());
        product.setImageUrl(fakeStoreProductResponseRecord.image());
        Category category=new Category();
        category.setName(fakeStoreProductResponseRecord.category());
        product.setCategory(category);
        FakeStoreRatingResponseRecord ratingResponseRecord=fakeStoreProductResponseRecord.rating();
        if(ratingResponseRecord!=null)
            product.setProductRating(new ProductRating(fakeStoreProductResponseRecord.rating().rate(),
                fakeStoreProductResponseRecord.rating().count()));
        return product;
    }
    private FakeStoreProductRequestRecord from(Product product){
        return new FakeStoreProductRequestRecord(product.getId(),product.getName(),product.getPrice(),
                product.getDescription(),product.getCategory().getName(),product.getImageUrl());
    }
}
