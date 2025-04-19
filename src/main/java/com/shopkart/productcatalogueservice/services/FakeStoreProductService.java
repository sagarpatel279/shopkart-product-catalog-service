package com.shopkart.productcatalogueservice.services;

import com.shopkart.productcatalogueservice.dtos.FakeStoreAPIProductDTO;
import com.shopkart.productcatalogueservice.dtos.ProductMapper;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
