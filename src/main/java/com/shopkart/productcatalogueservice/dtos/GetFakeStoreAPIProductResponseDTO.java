package com.shopkart.productcatalogueservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFakeStoreAPIProductResponseDTO {
    private FakeStoreAPIProductDTO fakeStoreAPIProductDTO;
    private ResponseStatus responseStatus;
}
