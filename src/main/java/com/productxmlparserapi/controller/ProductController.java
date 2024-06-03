package com.productxmlparserapi.controller;

import com.productxmlparserapi.controller.dto.response.AllProductsReponse;
import com.productxmlparserapi.controller.dto.response.ProductCountResponse;
import com.productxmlparserapi.controller.dto.response.SingleProductResponse;
import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.productxmlparserapi.model.Mapper.mapProductListToProductDtoList;
import static com.productxmlparserapi.model.Mapper.mapProductToProductDto;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/count")
    public ResponseEntity<ProductCountResponse> getProductCount(@RequestParam("file") MultipartFile file) {
        int productCount = productService.getProductCount(file);
        return ResponseEntity.ok(new ProductCountResponse(productCount));
    }

    @PostMapping(value = "/all")
    public ResponseEntity<AllProductsReponse> getAllProducts(@RequestParam("file") MultipartFile file) {
        List<Product> allProducts = productService.getAllProducts(file);
        return ResponseEntity.ok(new AllProductsReponse(mapProductListToProductDtoList(allProducts)));
    }

    @PostMapping(value = "/find")
    public ResponseEntity<SingleProductResponse> getProductByName(
            @RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        Product productByName = productService.getProductByName(file, name);
        return ResponseEntity.ok(new SingleProductResponse(mapProductToProductDto(productByName)));
    }
}


