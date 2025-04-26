package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.dtos.records.FakeStoreProductRecord;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("fakestore")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    private String fakeStoreUrl;
    public FakeStoreProductService(RestTemplate restTemplate,@Value("${fake-store-api-url}") String baseUrl){
        this.restTemplate=restTemplate;
        fakeStoreUrl=baseUrl+"/products";
    }
    @Override
    public Product createProduct(Product product){
        FakeStoreProductRecord requestRecord= ProductMapper.toFakeStoreProductRecord(product);
        requestRecord= restTemplate.postForObject(fakeStoreUrl,requestRecord,FakeStoreProductRecord.class);
        if(requestRecord==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return ProductMapper.toProduct(requestRecord);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws FakeStoreAPIException {
        FakeStoreProductRecord requestRecord =ProductMapper.toFakeStoreProductRecord(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductRecord> requestEntity=new HttpEntity<>(requestRecord,headers);
        ResponseEntity<FakeStoreProductRecord> responseEntity= restTemplate.exchange(fakeStoreUrl+"/"+productId.intValue(), HttpMethod.PATCH,requestEntity,FakeStoreProductRecord.class);
        if(responseEntity.getBody() == null){
            throw new FakeStoreAPIException("Something went wrong with fake-store api..");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public Product replaceProduct(Long productId, Product product) throws FakeStoreAPIException {
        System.out.println("URL: "+fakeStoreUrl+"/"+productId.intValue());
        FakeStoreProductRecord requestRecord =ProductMapper.toFakeStoreProductRecord(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductRecord> requestEntity=new HttpEntity<>(requestRecord,headers);
        ResponseEntity<FakeStoreProductRecord> responseEntity=restTemplate.exchange(fakeStoreUrl+"/"+productId.intValue(),HttpMethod.PUT,requestEntity,FakeStoreProductRecord.class);
        if(responseEntity.getBody() == null || !responseEntity.hasBody()) {
            throw new FakeStoreAPIException("Something went wrong with fake-store api...");
        }
        return ProductMapper.toProduct(requestRecord);
    }

    @Override
    public List<Product> getAllProducts() throws FakeStoreAPIException {
        ResponseEntity<FakeStoreProductRecord[]> responseEntity=restTemplate.getForEntity(fakeStoreUrl,FakeStoreProductRecord[].class);
        if(responseEntity.getBody() == null || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Something went wrong with fake-store api...");
        }
        return Arrays.stream(responseEntity.getBody()).
                map(ProductMapper::toProduct).toList();
    }

    @Override
    public Product getProduct(Long productId) throws FakeStoreAPIException {
        FakeStoreProductRecord requestRecord =restTemplate.getForObject(fakeStoreUrl+"/"+productId.intValue(),FakeStoreProductRecord.class);
        if(requestRecord ==null){
            throw new FakeStoreAPIException("Something went wrong with fake-store api...");
        }
        return ProductMapper.toProduct(requestRecord);
    }

    @Override
    public void deleteProduct(Long productId) throws FakeStoreAPIException {
        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    fakeStoreUrl + "/" + productId,
                    HttpMethod.DELETE,
                    null,
                    String.class
            );
            if(response.getStatusCode()!=HttpStatus.OK){
                throw new FakeStoreAPIException("Product could not be deleted");
            }
        }catch (RestClientException exp){
            throw new FakeStoreAPIException("Product could not be deleted");
        }
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return List.of();
    }

}
