package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.clients.fakestoreapi.FakeStoreApiProductClient;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreProductRequestRecord;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreProductResponseRecord;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreRatingResponseRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.ProductRating;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("fakestore")
public class FakeStoreProductService implements IProductService {

    private FakeStoreApiProductClient fakeStoreApiProductClient;
    public FakeStoreProductService(FakeStoreApiProductClient fakeStoreApiProductClient){
        this.fakeStoreApiProductClient = fakeStoreApiProductClient;
    }

    @Override
    public Product createProduct(Product product) {
        return from(fakeStoreApiProductClient.createFakeStoreProduct(from(product)));
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return from(fakeStoreApiProductClient.updateFakeStoreProduct(productId,from(product)));
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        return from(fakeStoreApiProductClient.replaceFakeStoreProduct(productId,from(product)));
    }

    @Override
    public List<Product> getAllProducts() {
        return from(fakeStoreApiProductClient.getAllFakeStoreProducts());
    }

    @Override
    public Product getProduct(Long productId) {
        return from(fakeStoreApiProductClient.getSingleFakeStoreProduct(productId));
    }

    @Override
    public void deleteProduct(Long productId) {
        fakeStoreApiProductClient.deleteFakeStoreProduct(productId);
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return from(fakeStoreApiProductClient.getProductsByCategory(category));
    }

    @Override
    public Page<Product> searchProductsInProductTitle(String query, int numberOfResults, int offset,String sortBy) {
        return null;
    }

    private List<Product> from(FakeStoreProductResponseRecord[] fakeStoreProductResponseRecords){
        return Arrays.stream(fakeStoreProductResponseRecords).map(this::from).toList();
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
