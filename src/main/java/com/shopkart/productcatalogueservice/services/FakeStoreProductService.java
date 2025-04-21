package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.dtos.FakeStoreAPIProductDTO;
import com.shopkart.productcatalogueservice.dtos.ProductDTO;
import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }
    @Override
    public Product createProduct(Product product) throws FakeStoreAPIException {
        FakeStoreAPIProductDTO requestDTO= ProductMapper.toFakeStoreAPIProductRequestDTO(product);
        requestDTO= restTemplate.postForObject("https://fakestoreapi.com/products",requestDTO,FakeStoreAPIProductDTO.class);
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
        ResponseEntity<FakeStoreAPIProductDTO> responseEntity= restTemplate.exchange("https://fakestoreapi.com/products/"+ProductId.intValue(), HttpMethod.PATCH,requestEntity,FakeStoreAPIProductDTO.class);
        if(responseEntity.getBody() == null){
            throw new FakeStoreAPIException("Something went wrong with fake-store api..");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
    }

    @Override
    public Product replaceProduct(Long ProductId, Product product) throws FakeStoreAPIException {
        FakeStoreAPIProductDTO requestDTO=ProductMapper.toFakeStoreAPIProductRequestDTO(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreAPIProductDTO> requestEntity=new HttpEntity<>(requestDTO,headers);
        ResponseEntity<FakeStoreAPIProductDTO> responseEntity=restTemplate.exchange("https://fakestoreapi.com/products/"+ProductId.intValue(),HttpMethod.PUT,requestEntity,FakeStoreAPIProductDTO.class);
        if(!responseEntity.hasBody()) {
            throw new FakeStoreAPIException("Something went wrong with fake-store api...");
        }
        return ProductMapper.toProduct(responseEntity.getBody());
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
    public Boolean deleteProduct(Long productId) {
        return null;
    }

}
