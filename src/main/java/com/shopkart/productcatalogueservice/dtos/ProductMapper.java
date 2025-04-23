package com.shopkart.productcatalogueservice.dtos;

import com.shopkart.productcatalogueservice.dtos.records.FakeStoreProductRecord;
import com.shopkart.productcatalogueservice.dtos.records.ProductRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.models.Rating;

import java.util.List;

public class ProductMapper {
//    public static ProductDTO toProductDTO(Product product){
//        ProductDTO productDTO =new ProductDTO();
//        productDTO.setId(product.getId());
//        productDTO.setName(product.getName());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setImageUrl(product.getImageUrl());
//        productDTO.setCategoryName(product.getCategory()!=null?product.getCategory().getName():null);
//        return productDTO;
//    }
//    public static Product toProduct(ProductDTO productDTO){
//        Product product =new Product();
//        product.setId(productDTO.getId());
//        product.setName(productDTO.getName());
//        product.setPrice(productDTO.getPrice());
//        product.setDescription(productDTO.getDescription());
//        product.setImageUrl(productDTO.getImageUrl());
//        product.setCategory(new Category(productDTO.getCategoryName()));
//        return product;
//    }
//    public static FakeStoreAPIProductDTO toFakeStoreAPIProductRequestDTO(Product product){
//        FakeStoreAPIProductDTO requestDTO = new FakeStoreAPIProductDTO();
//        requestDTO.setId(product.getId()!=null?product.getId().intValue():null);
//        requestDTO.setTitle(product.getName());
//        requestDTO.setCategory((product.getCategory()!=null)?product.getCategory().getName():null);
//        requestDTO.setDescription(product.getDescription());
//        requestDTO.setImage(product.getImageUrl());
//        requestDTO.setPrice(product.getPrice()!=null?product.getPrice().floatValue():null);
//        return requestDTO;
//    }
//    public static Product toProduct(FakeStoreAPIProductDTO requestDto){
//        Product product=new Product();
//        product.setId(requestDto.getId()!=null?requestDto.getId().longValue():null);
//        product.setName(requestDto.getTitle());
//        product.setPrice(requestDto.getPrice()!=null?requestDto.getPrice().doubleValue():null);
//        product.setDescription(requestDto.getDescription());
//        product.setImageUrl(requestDto.getImage());
//        product.setCategory(new Category(requestDto.getCategory()));
//        return product;
//    }
    public static Product toProduct(ProductRecord productRecord){
        Product product=new Product();
        product.setId(productRecord.id()!=null? productRecord.id() :null);
        product.setName(productRecord.name());
        product.setPrice(productRecord.price()!=null?productRecord.price():null);
        product.setDescription(productRecord.description());
        product.setImageUrl(productRecord.imageUrl());
        product.setCategory(new Category(productRecord.categoryName()));
        return product;
    }
    public static Product toProduct(FakeStoreProductRecord fakeStoreProductRecord){
        Product product=new Product();
        FakeStoreProductRecord.FakeStoreRatingRecord fakeStoreRatingRecord= fakeStoreProductRecord.rating();
        product.setId(fakeStoreProductRecord.id()!=null? fakeStoreProductRecord.id().longValue() :null);
        product.setName(fakeStoreProductRecord.title());
        product.setPrice(fakeStoreProductRecord.price()!=null? fakeStoreProductRecord.price().doubleValue():null);
        product.setDescription(fakeStoreProductRecord.description());
        product.setImageUrl(fakeStoreProductRecord.image());
        product.setCategory(new Category(fakeStoreProductRecord.category()));
        Rating rating=new Rating();
        rating.setRate(fakeStoreRatingRecord!=null?fakeStoreRatingRecord.rate():null);
        rating.setProduct(product);
        product.setRatings(List.of(rating));
        product.setCount(fakeStoreRatingRecord!=null?fakeStoreRatingRecord.count():null);
        return product;
    }
    public static ProductRecord toProductRecord(Product product){
        Double rate=product.getRatings()!=null?product.getRatings().getFirst().getRate():null;
        return new ProductRecord(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory()!=null? product.getCategory().getName():null,
                product.getImageUrl(),
                new ProductRecord.RatingResponseRecord(rate,product.getCount()));
    }
    public static FakeStoreProductRecord toFakeStoreProductRecord(Product product){
        Double rate=product.getRatings()!=null?product.getRatings().getFirst().getRate():null;
        return new FakeStoreProductRecord(
                product.getId()!=null?product.getId().intValue():null,
                product.getName(),
                product.getPrice()!=null?product.getPrice().floatValue():null,
                product.getDescription(),
                product.getCategory()!=null? product.getCategory().getName():null,
                product.getImageUrl(),
                new FakeStoreProductRecord.FakeStoreRatingRecord(rate,product.getCount()));
    }
    public static FakeStoreProductRecord toFakeStoreProductRecord(ProductRecord productRecord){
        return new FakeStoreProductRecord(
                productRecord.id()!=null? productRecord.id().intValue():null,
                productRecord.name(),
                productRecord.price()!=null? productRecord.price().floatValue():null,
                productRecord.description(),
                productRecord.categoryName(),
                productRecord.imageUrl(),
                new FakeStoreProductRecord.FakeStoreRatingRecord(productRecord.rating().rate(), productRecord.rating().count())
                );
    }
    public static ProductRecord toProductRecord(FakeStoreProductRecord requestRecord){
        FakeStoreProductRecord.FakeStoreRatingRecord fakeStoreRatingRecord= requestRecord.rating();
        return new ProductRecord(
                requestRecord.id()!=null? requestRecord.id().longValue():null,
                requestRecord.title(),
                requestRecord.price()!=null? requestRecord.price().doubleValue():null,
                requestRecord.description(),
                requestRecord.image(),
                requestRecord.category(),
                new ProductRecord.RatingResponseRecord(fakeStoreRatingRecord!=null?fakeStoreRatingRecord.rate():null, fakeStoreRatingRecord!=null?fakeStoreRatingRecord.count():null));
    }
}

