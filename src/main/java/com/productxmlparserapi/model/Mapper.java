package com.productxmlparserapi.model;

import com.productxmlparserapi.controller.dto.response.ProductDto;

import java.util.List;

public interface Mapper {
    static List<ProductDto> mapProductListToProductDtoList(List<Product> products){
        return products.stream()
                .map(Mapper::mapProductToProductDto)
                .toList();
    }

    static ProductDto mapProductToProductDto(Product product){
        return new ProductDto(
                product.id(),
                product.name(),
                product.category(),
                product.partNumberNR(),
                product.companyName(),
                product.active()
        );
    }
}
