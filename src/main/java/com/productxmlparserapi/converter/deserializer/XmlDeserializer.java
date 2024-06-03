package com.productxmlparserapi.converter.deserializer;

import org.springframework.web.multipart.MultipartFile;

public interface XmlDeserializer<T> {
    T fromXml(MultipartFile file);
}
