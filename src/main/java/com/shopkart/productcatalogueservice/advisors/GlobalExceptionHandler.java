package com.shopkart.productcatalogueservice.advisors;

import com.shopkart.productcatalogueservice.dtos.ExceptionResponseDTO;
import com.shopkart.productcatalogueservice.dtos.GetFakeStoreAPIProductResponseDTO;
import com.shopkart.productcatalogueservice.dtos.ResponseStatus;
import com.shopkart.productcatalogueservice.exceptions.FakeStoreAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FakeStoreAPIException.class)
    public ResponseEntity<?> handleFakeStoreAPIException(FakeStoreAPIException exception){
        GetFakeStoreAPIProductResponseDTO responseDTO=new GetFakeStoreAPIProductResponseDTO();
        responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralCheckedException(Exception exp){
        ExceptionResponseDTO responseDTO=new ExceptionResponseDTO();
        responseDTO.setMessage("Something went wrong at server side...Please try again..!"+exp.getMessage());
        responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleGeneralRuntimeException(RuntimeException exp){
        ExceptionResponseDTO responseDTO=new ExceptionResponseDTO();
        responseDTO.setMessage("Something went wrong with service...!: "+ exp.getMessage());
        responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
