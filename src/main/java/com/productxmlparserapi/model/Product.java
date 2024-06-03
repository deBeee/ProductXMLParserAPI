package com.productxmlparserapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record Product(
        @JacksonXmlProperty(isAttribute = true, localName = "id")
        int id,
        @JacksonXmlProperty(localName = "Name")
        String name,
        @JacksonXmlProperty(localName = "Category")
        String category,
        @JacksonXmlProperty(localName = "PartNumberNR")
        String partNumberNR,
        @JacksonXmlProperty(localName = "CompanyName")
        String companyName,
        @JacksonXmlProperty(localName = "Active")
        boolean active
) {
}
