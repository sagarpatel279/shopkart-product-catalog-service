package com.shopkart.productcatalogueservice.clients.fakestoreapi;

import com.shopkart.productcatalogueservice.clients.fakestoreapi.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreCategoryRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApiCategoryClient {

    private RestTemplateBuilder restTemplateBuilder;
    private String categoriesRequestBaseUrl;

    public FakeStoreApiCategoryClient(RestTemplateBuilder restTemplateBuilder,
                                      @Value("${fakestore.api.url}") String fakeStoreApiBaseUrl,
                                      @Value("${fakestore.api.paths.products}") String productsFakeStoreApiPath,
                                      @Value("${fakestore.api.paths.categories}") String categoriesFakeStoreApiPath){
        this.restTemplateBuilder=restTemplateBuilder;
        this.categoriesRequestBaseUrl=fakeStoreApiBaseUrl+productsFakeStoreApiPath +categoriesFakeStoreApiPath;
    }
    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod,
                                                  @Nullable Object requestBody,Class<T> responseType,Object... uriVariables){
        RestTemplate restTemplate=restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class).build();
        RequestCallback requestCallback=restTemplate.httpEntityCallback(requestBody,responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor=restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url,httpMethod,requestCallback,responseExtractor,uriVariables);
    }

    public String[] getAllFakeStoreCategories(){
        ResponseEntity<String[]> responseEntity=requestForEntity(categoriesRequestBaseUrl,
                HttpMethod.GET,null,String[].class);
        if(responseEntity==null || !responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Categories could not be found on Fake Store API....");
        }
        return responseEntity.getBody();
    }
}
