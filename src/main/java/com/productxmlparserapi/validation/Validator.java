package com.productxmlparserapi.validation;

import java.util.Map;

@FunctionalInterface
public interface Validator <T>{
    Map<String, String> validate(T data);

    static <T> boolean validate(T data, Validator<T> validator){
        return validator.validate(data).isEmpty();
    }
}
