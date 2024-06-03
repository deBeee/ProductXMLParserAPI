package com.productxmlparserapi.service;

import com.productxmlparserapi.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    int getProductCount(MultipartFile file);
    List<Product> getAllProducts(MultipartFile file);
    Product getProductByName(MultipartFile file, String name);
}
