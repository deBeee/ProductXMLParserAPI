package com.productxmlparserapi.error.xmlmapper;

import com.productxmlparserapi.error.xmlmapper.dto.XmlMapperErrorResponse;
import com.productxmlparserapi.error.xmlmapper.exception.XmlDatabindException;
import com.productxmlparserapi.error.xmlmapper.exception.XmlIOException;
import com.productxmlparserapi.error.xmlmapper.exception.XmlStreamReadException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class XmlMapperErrorHandler {

    @ExceptionHandler(XmlStreamReadException.class)
    public ResponseEntity<XmlMapperErrorResponse> handleXmlStreamReadException(XmlStreamReadException ex) {
        log.warn("XmlStreamReadException thrown while parsing XML data");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new XmlMapperErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Failed to read XML stream, please ensure the file is well-formed and try again",
                        ex.getCause().getMessage())
                );
    }

    @ExceptionHandler(XmlDatabindException.class)
    public ResponseEntity<XmlMapperErrorResponse> handleXmlDatabindException(XmlDatabindException ex) {
        log.warn("XmlDatabindException thrown while parsing XML data");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new XmlMapperErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Failed to parse XML data, please check the file format correctness and ensure it matches the expected structure",
                        ex.getCause().getMessage())
                );
    }

    @ExceptionHandler(XmlIOException.class)
    public ResponseEntity<XmlMapperErrorResponse> handleXmlIOException(XmlIOException ex) {
        log.warn("XmlIOException thrown while parsing XML data");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new XmlMapperErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "I/O error occurred while processing the XML file, please check the file and try again",
                        ex.getCause().getMessage())
                );
    }
}
