package com.shopkart.productcatalogueservice.dtos;

import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;

public class ProductMapper {
    public static ProductDTO toProductDTO(Product product){
        ProductDTO productDTO =new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setCategoryName(product.getCategory()!=null?product.getCategory().getName():null);
        return productDTO;
    }
    public static Product toProduct(ProductDTO productDTO){
        Product product =new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(new Category(productDTO.getCategoryName()));
        return product;
    }
    public static FakeStoreAPIProductDTO toFakeStoreAPIProductRequestDTO(Product product){
        FakeStoreAPIProductDTO requestDTO = new FakeStoreAPIProductDTO();
        requestDTO.setId(product.getId()!=null?product.getId().intValue():null);
        requestDTO.setTitle(product.getName());
        requestDTO.setCategory((product.getCategory()!=null)?product.getCategory().getName():null);
        requestDTO.setDescription(product.getDescription());
        requestDTO.setImage(product.getImageUrl());
        requestDTO.setPrice(product.getPrice()!=null?product.getPrice().floatValue():null);
        return requestDTO;
    }
    public static Product toProduct(FakeStoreAPIProductDTO requestDto){
        Product product=new Product();
        product.setId(requestDto.getId()!=null?requestDto.getId().longValue():null);
        product.setName(requestDto.getTitle());
        product.setPrice(requestDto.getPrice()!=null?requestDto.getPrice().doubleValue():null);
        product.setDescription(requestDto.getDescription());
        product.setImageUrl(requestDto.getImage());
        product.setCategory(new Category(requestDto.getCategory()));
        return product;
    }
}
