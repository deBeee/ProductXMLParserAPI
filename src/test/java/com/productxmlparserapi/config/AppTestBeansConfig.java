package com.productxmlparserapi.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.productxmlparserapi.converter.deserializer.XmlDeserializer;
import com.productxmlparserapi.converter.deserializer.impl.ProductXmlDeserializer;
import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.model.Products;
import com.productxmlparserapi.validation.Validator;
import com.productxmlparserapi.validation.impl.ProductValidator;
import org.springframework.context.annotation.Bean;

public class AppTestBeansConfig {

    @Bean
    public XmlMapper xmlMapper() {
        return XmlMapper.builder().build();
    }

    @Bean
    public XmlDeserializer<Products> xmlDeserializer(XmlMapper xmlMapper){
        return new ProductXmlDeserializer(xmlMapper);
    }

    @Bean
    public Validator<Product> validator(){
        return new ProductValidator();
    }
}
