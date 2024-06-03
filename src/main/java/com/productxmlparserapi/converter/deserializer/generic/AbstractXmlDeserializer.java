package com.productxmlparserapi.converter.deserializer.generic;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.productxmlparserapi.converter.deserializer.XmlDeserializer;
import com.productxmlparserapi.error.xmlmapper.exception.XmlDatabindException;
import com.productxmlparserapi.error.xmlmapper.exception.XmlIOException;
import com.productxmlparserapi.error.xmlmapper.exception.XmlStreamReadException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

@RequiredArgsConstructor
public abstract class AbstractXmlDeserializer<T> implements XmlDeserializer<T> {

    private final XmlMapper xmlMapper;

    @SuppressWarnings("unchecked")
    private final Class<T> type =
            (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public T fromXml(MultipartFile file) {
        try {
            return xmlMapper.readValue(file.getInputStream(), type);
        } catch (StreamReadException sre) {
            throw new XmlStreamReadException(sre);
        } catch (DatabindException dbe) {
            throw new XmlDatabindException(dbe);
        } catch (IOException ioe) {
            throw new XmlIOException(ioe);
        }
    }
}
