package com.productxmlparserapi.service.impl;

import com.productxmlparserapi.converter.product.FileToProductConverter;
import com.productxmlparserapi.error.controller.exception.ProductNotFoundException;
import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public final FileToProductConverter fileToProductConverter;

    public int getProductCount(MultipartFile file) {
        List<Product> products = fileToProductConverter.convert(file);
        return products.size();
    }

    public List<Product> getAllProducts(MultipartFile file) {
        return fileToProductConverter.convert(file);
    }

    public Product getProductByName(MultipartFile file, String name) {
        List<Product> products = fileToProductConverter.convert(file);
        return products.stream()
                .filter(product -> product.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with name %s not found".formatted(name), name));
    }
}

