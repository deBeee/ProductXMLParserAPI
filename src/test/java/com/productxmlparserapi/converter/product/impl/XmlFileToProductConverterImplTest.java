package com.productxmlparserapi.converter.product.impl;

import com.productxmlparserapi.config.AppTestBeansConfig;
import com.productxmlparserapi.converter.deserializer.XmlDeserializer;
import com.productxmlparserapi.converter.product.FileToProductConverter;
import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.model.Products;
import com.productxmlparserapi.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.productxmlparserapi.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = AppTestBeansConfig.class)
class XmlFileToProductConverterImplTest {

    @Autowired
    private Validator<Product> productValidator;

    @Mock
    private XmlDeserializer<Products> productsXmlDeserializer;

    private FileToProductConverter fileToProductConverter;

    @BeforeEach
    void setUp() {
        fileToProductConverter = new XmlFileToProductConverterImpl(productsXmlDeserializer, productValidator);
    }

    @Test
    @DisplayName("When all data is correct")
    void testConversionWithCorrectData() {
        when(productsXmlDeserializer.fromXml(ArgumentMatchers.any()))
                .thenReturn(new Products(List.of(
                        PRODUCT1,
                        PRODUCT2,
                        PRODUCT3
                )));

        assertThat(fileToProductConverter.convert(ArgumentMatchers.any()))
                .hasSize(3)
                .containsExactly(PRODUCT1, PRODUCT2, PRODUCT3);
    }

    @Test
    @DisplayName("When not all data is correct")
    void testConversionWithNotAllCorrectData() {
        when(productsXmlDeserializer.fromXml(ArgumentMatchers.any()))
                .thenReturn(new Products(List.of(
                        INVALID_PRODUCT,
                        PRODUCT2,
                        PRODUCT3
                )));

        assertThat(fileToProductConverter.convert(ArgumentMatchers.any()))
                .hasSize(2)
                .containsExactly(PRODUCT2, PRODUCT3);
    }
}
