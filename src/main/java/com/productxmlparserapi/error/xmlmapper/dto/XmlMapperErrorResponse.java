package com.productxmlparserapi.error.xmlmapper.dto;

public record XmlMapperErrorResponse(int status, String message, String details) {
}
