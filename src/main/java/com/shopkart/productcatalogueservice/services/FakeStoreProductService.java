package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.dtos.records.FakeStoreProductRecord;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@Service
@Profile("fakestore")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    private RestTemplateBuilder restTemplateBuilder;


    private String productRequestsBaseUrl ;
    private String productRequestsByCategoryBaseUrl;

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder,
       @Value("${fakestore.api.url}") String fakeStoreApiUrl,
       @Value("${fakestore.api.paths.product}") String fakeStoreProductsApiPath,
        @Value("${fakestore.api.paths.category}") String fakeStoreCategoryApiPath){
        this.restTemplateBuilder=restTemplateBuilder;
        this.productRequestsBaseUrl  = fakeStoreApiUrl + fakeStoreProductsApiPath;
        this.productRequestsByCategoryBaseUrl=this.productRequestsBaseUrl+fakeStoreCategoryApiPath;
    }
    private <T> ResponseEntity<T> requestForEntity(String url,HttpMethod httpMethod, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();

        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
    @Override
    public Product createProduct(Product product){
        ResponseEntity<FakeStoreProductRecord> responseEntity= requestForEntity(productRequestsBaseUrl,
                HttpMethod.POST,ProductMapper.toFakeStoreProductRecord(product),FakeStoreProductRecord.class);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody()==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws FakeStoreAPIException {
        ResponseEntity<FakeStoreProductRecord> responseEntity= requestForEntity(productRequestsBaseUrl+"/{id}",
                HttpMethod.PATCH,ProductMapper.toFakeStoreProductRecord(product),FakeStoreProductRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody()==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public Product replaceProduct(Long productId, Product product) throws FakeStoreAPIException {
        ResponseEntity<FakeStoreProductRecord> responseEntity= requestForEntity(productRequestsBaseUrl+"/{id}",
                HttpMethod.PUT,ProductMapper.toFakeStoreProductRecord(product),FakeStoreProductRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody()==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public List<Product> getAllProducts() throws FakeStoreAPIException {
        System.out.println("API URL: "+productRequestsBaseUrl);
        ResponseEntity<FakeStoreProductRecord[]> responseEntity= requestForEntity(productRequestsBaseUrl,
                HttpMethod.GET,null,FakeStoreProductRecord[].class);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody()==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return Arrays.stream(responseEntity.getBody()).
                map(ProductMapper::toProduct).toList();
    }

    @Override
    public Product getProduct(Long productId) throws FakeStoreAPIException {
        ResponseEntity<FakeStoreProductRecord> responseEntity= requestForEntity(productRequestsBaseUrl+"/{id}",
                HttpMethod.GET,null,FakeStoreProductRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody()==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public void deleteProduct(Long productId) throws FakeStoreAPIException {
        ResponseEntity<FakeStoreProductRecord> responseEntity= requestForEntity(productRequestsBaseUrl+"/{id}",
                HttpMethod.DELETE,null,FakeStoreProductRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody()==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        ResponseEntity<FakeStoreProductRecord[]> responseEntity= requestForEntity(productRequestsByCategoryBaseUrl+"/{category}",
                HttpMethod.GET,null,FakeStoreProductRecord[].class,category);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody()==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return Arrays.stream(responseEntity.getBody()).
                map(ProductMapper::toProduct).toList();
    }

}
