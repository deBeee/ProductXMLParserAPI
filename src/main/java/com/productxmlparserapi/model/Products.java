package com.productxmlparserapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public record Products(
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Product")
        List<Product> products
) {
}
