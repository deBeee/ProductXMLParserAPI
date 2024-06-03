package com.productxmlparserapi;

import com.productxmlparserapi.model.Product;

public interface TestData {

    // product-valid.xml contains these products
    Product PRODUCT1 = new Product(1, "apple", "fruit", "2303-E1A-G-M-W209B-VM", "FruitsAll", true);
    Product PRODUCT2 = new Product(2, "orange", "fruit", "5603-J1A-G-M-W982F-PO", "FruitsAll", false);
    Product PRODUCT3 = new Product(3, "glass", "dish", "9999-E7R-Q-M-K287B-YH", "HomeHome", true);


    // sample product with invalid partNumberNR
    Product INVALID_PRODUCT = new Product(4, "apple", "fruit", "2-3-03-E1A-G-M-W-20-9B-VM", "FruitsAll", true);
}
