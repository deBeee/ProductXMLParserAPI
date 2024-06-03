package com.productxmlparserapi.converter;

public interface Converter <T, U>{
    U convert(T t);
}
