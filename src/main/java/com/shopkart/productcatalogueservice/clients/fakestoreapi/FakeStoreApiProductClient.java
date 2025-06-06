package com.shopkart.productcatalogueservice.clients.fakestoreapi;

import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreProductRequestRecord;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.records.FakeStoreProductResponseRecord;
import com.shopkart.productcatalogueservice.clients.fakestoreapi.exceptions.FakeStoreAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class FakeStoreApiProductClient {
    private RestTemplateBuilder restTemplateBuilder;
    private String productsRequestsBaseUrl ;
    private String productsRequestsByCategoryBaseUrl;

    public FakeStoreApiProductClient(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${fakestore.api.url}") String fakeStoreApiUrl,
                                     @Value("${fakestore.api.paths.products}") String fakeStoreProductsApiPath,
                                     @Value("${fakestore.api.paths.category}") String fakeStoreCategoryApiPath){
        this.restTemplateBuilder=restTemplateBuilder;
        this.productsRequestsBaseUrl  = fakeStoreApiUrl + fakeStoreProductsApiPath;
        this.productsRequestsByCategoryBaseUrl=this.productsRequestsBaseUrl+fakeStoreCategoryApiPath;
    }

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod,
                                                   @Nullable Object request,Class<T> responseType,
                                                   Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder
                .requestFactory(HttpComponentsClientHttpRequestFactory.class).build();

        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public FakeStoreProductResponseRecord createFakeStoreProduct(FakeStoreProductRequestRecord fakeStoreProductRequestRecord) {
        ResponseEntity<FakeStoreProductResponseRecord> responseEntity= requestForEntity(productsRequestsBaseUrl,
                HttpMethod.POST, fakeStoreProductRequestRecord, FakeStoreProductResponseRecord.class);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Product could not created on Fake Store API...!");
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductResponseRecord updateFakeStoreProduct(Long productId, FakeStoreProductRequestRecord fakeStoreProductRequestRecord){
        ResponseEntity<FakeStoreProductResponseRecord> responseEntity= requestForEntity(productsRequestsBaseUrl+"/{id}",
                HttpMethod.PATCH, fakeStoreProductRequestRecord, FakeStoreProductResponseRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Product could not be updated on Fake Store API...!");
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductResponseRecord replaceFakeStoreProduct(Long productId, FakeStoreProductRequestRecord fakeStoreProductRequestRecord){
        ResponseEntity<FakeStoreProductResponseRecord> responseEntity= requestForEntity(productsRequestsBaseUrl+"/{id}",
                HttpMethod.PUT, fakeStoreProductRequestRecord, FakeStoreProductResponseRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Product could not be replaced on Fake Store API...!");
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductResponseRecord getSingleFakeStoreProduct(Long productId){
        ResponseEntity<FakeStoreProductResponseRecord> responseEntity= requestForEntity(productsRequestsBaseUrl+"/{id}",
                HttpMethod.GET,null, FakeStoreProductResponseRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Product could not be found by Product Id: "+productId+" on Fake Store API...!");
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductResponseRecord[] getAllFakeStoreProducts(){
        ResponseEntity<FakeStoreProductResponseRecord[]> responseEntity= requestForEntity(productsRequestsBaseUrl,
                HttpMethod.GET,null, FakeStoreProductResponseRecord[].class);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Products could not be found on Fake Store API...!");
        }
        return responseEntity.getBody();
    }

    public void deleteFakeStoreProduct(Long productId){
        ResponseEntity<FakeStoreProductResponseRecord> responseEntity= requestForEntity(productsRequestsBaseUrl+"/{id}",
                HttpMethod.DELETE,null, FakeStoreProductResponseRecord.class,productId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Products could not be deleted on Fake Store API...!");
        }
    }

    public FakeStoreProductResponseRecord[] getProductsByCategory(String categoryName){
        ResponseEntity<FakeStoreProductResponseRecord[]> responseEntity= requestForEntity(productsRequestsByCategoryBaseUrl+"/{categoryName}",
                HttpMethod.GET,null, FakeStoreProductResponseRecord[].class,categoryName);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Product could not be found by Category : "+categoryName+" on Fake Store API...!");
        }
        return responseEntity.getBody();
    }
}
