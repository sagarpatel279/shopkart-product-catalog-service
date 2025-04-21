package com.shopkart.productcatalogueservice.advisors;

import com.shopkart.productcatalogueservice.dtos.ApiResponse;
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
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralCheckedException(Exception exp){
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("Something went wrong at server side...Please try again..!");
        apiResponse.setStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleGeneralRuntimeException(RuntimeException exp){
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("Something went wrong with service...!: ");
        apiResponse.setStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
