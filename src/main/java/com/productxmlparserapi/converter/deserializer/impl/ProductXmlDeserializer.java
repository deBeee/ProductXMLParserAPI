package com.productxmlparserapi.converter.deserializer.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.productxmlparserapi.converter.deserializer.XmlDeserializer;
import com.productxmlparserapi.converter.deserializer.generic.AbstractXmlDeserializer;
import com.productxmlparserapi.model.Products;
import org.springframework.stereotype.Component;

@Component
public class ProductXmlDeserializer extends AbstractXmlDeserializer<Products> implements XmlDeserializer<Products> {

    public ProductXmlDeserializer(XmlMapper xmlMapper) {
        super(xmlMapper);
    }
}
