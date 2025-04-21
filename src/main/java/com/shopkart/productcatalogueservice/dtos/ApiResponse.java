package com.shopkart.productcatalogueservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String message;
    private ResponseStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
    private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.systemDefault());

}