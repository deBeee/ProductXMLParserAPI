package com.productxmlparserapi.converter.product.impl;

import com.productxmlparserapi.converter.deserializer.XmlDeserializer;
import com.productxmlparserapi.converter.product.FileToProductConverter;
import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.model.Products;
import com.productxmlparserapi.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class XmlFileToProductConverterImpl implements FileToProductConverter {

    private final XmlDeserializer<Products> productsXmlDeserializer;
    private final Validator<Product> productValidator;

    @Override
    public List<Product> convert(MultipartFile file) {
        return productsXmlDeserializer
                .fromXml(file)
                .products()
                .stream()
                .filter(product -> Validator.validate(product, productValidator))
                .toList();
    }
}
