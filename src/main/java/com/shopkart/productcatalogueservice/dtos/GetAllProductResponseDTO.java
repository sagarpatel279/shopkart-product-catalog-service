package com.shopkart.productcatalogueservice.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class GetAllProductResponseDTO {
    private List<ProductDTO> productDTOs;
    private ResponseStatus responseStatus;
}
