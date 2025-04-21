package com.shopkart.productcatalogueservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseDTO {
    private String message;
    private ResponseStatus responseStatus;
}
