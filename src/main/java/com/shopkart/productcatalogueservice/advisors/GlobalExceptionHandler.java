package com.shopkart.productcatalogueservice.advisors;

import com.shopkart.productcatalogueservice.clients.fakestoreapi.exceptions.FakeStoreAPIException;
import com.shopkart.productcatalogueservice.exceptions.*;
import jakarta.el.MethodNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralCheckedException(Exception exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleGeneralRuntimeException(RuntimeException exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(FakeStoreAPIException.class)
    public ResponseEntity<?> handleFakeStoreAPIException(FakeStoreAPIException exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductExistWithCategoryException.class)
    public ResponseEntity<?> handleProductExistWithCategoryException(ProductExistWithCategoryException exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<?> handleCategoryAlreadyExistException(CategoryAlreadyExistException exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryOnDeleteException.class)
    public ResponseEntity<?> handleCategoryOnDeletionException(CategoryOnDeleteException exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException exp){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("message",exp.getMessage());
        errorResponse.put("status",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidFormat(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", HttpStatus.BAD_REQUEST,
                        "message", "Invalid request body: " + ex.getMostSpecificCause().getMessage()
                )
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodValidationExceptions(MethodArgumentNotValidException exp){
        Map<String, List<String>> errorResponse=new HashMap<>();
        exp.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorResponse.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>()).add(fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodNotFoundException.class)
    public ResponseEntity<?> handleMethodValidationExceptions(MethodNotFoundException exp){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("message",exp.getMessage());
            errorResponse.put("status",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "API not found");
        error.put("path", ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
