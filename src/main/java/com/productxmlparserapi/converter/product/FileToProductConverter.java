package com.productxmlparserapi.converter.product;

import com.productxmlparserapi.converter.Converter;
import com.productxmlparserapi.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileToProductConverter extends Converter<MultipartFile, List<Product>> {
}
