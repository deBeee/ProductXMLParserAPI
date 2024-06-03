package com.productxmlparserapi.error.controller.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException{
    private String name;

    public ProductNotFoundException(String message, String name) {
        super(message);
        this.name = name;
    }
}
