package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.dtos.FakeStoreAPIProductDTO;
import com.shopkart.productcatalogueservice.dtos.ProductDTO;
import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    private final String baseUrl;
    private String fakeStoreUrl;
    public FakeStoreProductService(RestTemplate restTemplate,@Value("${fake-store-api-url}") String baseUrl){
        this.restTemplate=restTemplate;
        this.baseUrl=baseUrl;
        fakeStoreUrl=baseUrl+"/products";
    }
    @Override
    public Product createProduct(Product product) throws FakeStoreAPIException {
        FakeStoreAPIProductDTO requestDTO= ProductMapper.toFakeStoreAPIProductRequestDTO(product);
        requestDTO= restTemplate.postForObject(fakeStoreUrl,requestDTO,FakeStoreAPIProductDTO.class);
        if(requestDTO==null){
            throw new FakeStoreAPIException("Something went wrong");
        }
        return ProductMapper.toProduct(requestDTO);
    }

    @Override
    public Product updateProduct(Long ProductId, Product product) throws FakeStoreAPIException {
        FakeStoreAPIProductDTO requestDTO=ProductMapper.toFakeStoreAPIProductRequestDTO(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreAPIProductDTO> requestEntity=new HttpEntity<>(requestDTO,headers);
        ResponseEntity<FakeStoreAPIProductDTO> responseEntity= restTemplate.exchange(fakeStoreUrl+"/"+ProductId.intValue(), HttpMethod.PATCH,requestEntity,FakeStoreAPIProductDTO.class);
        if(responseEntity.getBody() == null){
            throw new FakeStoreAPIException("Something went wrong with fake-store api..");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public Product replaceProduct(Long ProductId, Product product) throws FakeStoreAPIException {
        System.out.println("URL: "+fakeStoreUrl+"/"+ProductId.intValue());
        FakeStoreAPIProductDTO requestDTO=ProductMapper.toFakeStoreAPIProductRequestDTO(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreAPIProductDTO> requestEntity=new HttpEntity<>(requestDTO,headers);
        ResponseEntity<FakeStoreAPIProductDTO> responseEntity=restTemplate.exchange(fakeStoreUrl+"/"+ProductId.intValue(),HttpMethod.PUT,requestEntity,FakeStoreAPIProductDTO.class);
        if(responseEntity.getBody() == null || !responseEntity.hasBody()) {
            throw new FakeStoreAPIException("Something went wrong with fake-store api...");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public List<Product> getAllProducts() throws FakeStoreAPIException {
        ResponseEntity<FakeStoreAPIProductDTO[]> responseEntity=restTemplate.getForEntity(fakeStoreUrl,FakeStoreAPIProductDTO[].class);
        if(responseEntity.getBody() == null || !responseEntity.hasBody()){
            throw new FakeStoreAPIException("Something went wrong with fake-store api...");
        }
        return Arrays.stream(responseEntity.getBody()).map(ProductMapper::toProduct).toList();
    }

    @Override
    public Product getProduct(Long productId) throws FakeStoreAPIException {
        FakeStoreAPIProductDTO fakeStoreAPIProductDTO=restTemplate.getForObject(fakeStoreUrl+"/"+productId.intValue(),FakeStoreAPIProductDTO.class);
        if(fakeStoreAPIProductDTO==null){
            throw new FakeStoreAPIException("Something went wrong with fake-store api...");
        }
        return ProductMapper.toProduct(fakeStoreAPIProductDTO);
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
//            System.out.println("Delete Response: " + response.getBody());

        }catch (RestClientException exp){
            throw new FakeStoreAPIException("Product could not be deleted");
        }
    }

}
