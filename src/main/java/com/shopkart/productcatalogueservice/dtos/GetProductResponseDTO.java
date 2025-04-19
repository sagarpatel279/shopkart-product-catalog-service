package com.shopkart.productcatalogueservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductResponseDTO {
    private ProductDTO productDTO;
    private ResponseStatus responseStatus;
}
