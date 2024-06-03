package com.productxmlparserapi.error.controller;

import com.productxmlparserapi.controller.ProductController;
import com.productxmlparserapi.error.controller.dto.ProductNotFoundErrorReponse;
import com.productxmlparserapi.error.controller.exception.ProductNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ProductController.class)
@Log4j2
public class ProductControllerErrorHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundErrorReponse> handlerProductNotFoundException(ProductNotFoundException exception){
        log.warn("ProductNotFoundException thrown while performing request");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ProductNotFoundErrorReponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), exception.getName()));
    }
}
