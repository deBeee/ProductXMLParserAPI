package com.productxmlparserapi.converter.deserializer.impl;

import com.productxmlparserapi.config.AppTestBeansConfig;
import com.productxmlparserapi.converter.deserializer.XmlDeserializer;
import com.productxmlparserapi.error.xmlmapper.exception.XmlDatabindException;
import com.productxmlparserapi.error.xmlmapper.exception.XmlStreamReadException;
import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.model.Products;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestBeansConfig.class)
class ProductXmlDeserializerTest {

    @Autowired
    private XmlDeserializer<Products> productXmlDeserializer;

    @Test
    @DisplayName("When XML file is valid, it should be deserialized correctly")
    void testValidXmlDeserialization() throws IOException {
        ClassPathResource resource = new ClassPathResource("products-valid.xml");
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "products-valid.xml",
                "application/xml",
                Files.readAllBytes(resource.getFile().toPath())
        );

        List<Product> expectedProducts = List.of(
                new Product(1, "apple", "fruit", "2303-E1A-G-M-W209B-VM", "FruitsAll", true),
                new Product(2, "orange", "fruit", "5603-J1A-G-M-W982F-PO", "FruitsAll", false),
                new Product(3, "glass", "dish", "9999-E7R-Q-M-K287B-YH", "HomeHome", true)
        );

        Products products = productXmlDeserializer.fromXml(file);
        List<Product> productList = products.products();

        Assertions.assertAll(
                () -> assertThat(products).isNotNull(),
                () -> assertThat(productList).isNotNull(),
                () -> assertThat(productList).hasSize(3),
                () -> assertThat(productList).containsAll(expectedProducts)
        );
    }

    @Test
    @DisplayName("When XML file structure is invalid, it should throw XmlDatabindException")
    void testInvalidXmlDeserializationWithXmlDatabindException() throws IOException {
        ClassPathResource resource = new ClassPathResource("products-invalid.xml");
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "products-invalid.xml",
                "application/xml",
                Files.readAllBytes(resource.getFile().toPath())
        );

        assertThrows(XmlDatabindException.class, () -> productXmlDeserializer.fromXml(file));
    }

    @Test
    @DisplayName("When XML file does not contain xml content, it should throw XmlStreamReadException")
    void testInvalidXmlDeserializationWithXmlStreamReadException() {
        String invalidXmlContent = "I am not a xml content";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "invalid-product.xml",
                "application/xml",
                invalidXmlContent.getBytes()
        );
        assertThrows(XmlStreamReadException.class, () -> productXmlDeserializer.fromXml(file));
    }
}
