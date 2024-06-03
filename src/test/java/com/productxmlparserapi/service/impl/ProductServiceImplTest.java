package com.productxmlparserapi.service.impl;

import com.productxmlparserapi.converter.product.FileToProductConverter;
import com.productxmlparserapi.error.controller.exception.ProductNotFoundException;
import com.productxmlparserapi.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static com.productxmlparserapi.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private FileToProductConverter fileToProductConverter;

    @InjectMocks
    private ProductServiceImpl productService;

    private MockMultipartFile file;


    @BeforeEach
    void setUp() {
        file = new MockMultipartFile("file", "products-valid.xml", "application/xml", new byte[]{});
    }

    @Test
    @DisplayName("When getting product count from valid XML file")
    void testGetProductCount() {
        when(fileToProductConverter.convert(ArgumentMatchers.any()))
                .thenReturn(List.of(PRODUCT1, PRODUCT2, PRODUCT3));

        int count = productService.getProductCount(file);

        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("When getting all products from valid XML file")
    void testGetAllProducts() {
        when(fileToProductConverter.convert(ArgumentMatchers.any()))
                .thenReturn(List.of(PRODUCT1, PRODUCT2, PRODUCT3));

        List<Product> products = productService.getAllProducts(file);

        assertThat(products).containsExactly(PRODUCT1, PRODUCT2, PRODUCT3);
    }

    @Test
    @DisplayName("When getting product by name from valid XML file")
    void testGetProductByName() {
        when(fileToProductConverter.convert(ArgumentMatchers.any()))
                .thenReturn(List.of(PRODUCT1, PRODUCT2, PRODUCT3));

        Product product = productService.getProductByName(file, "orange");

        assertThat(product).isEqualTo(PRODUCT2);
    }

    @Test
    @DisplayName("When product by name not found")
    void testGetProductByNameNotFound() {
        when(fileToProductConverter.convert(ArgumentMatchers.any()))
                .thenReturn(List.of(PRODUCT1, PRODUCT2, PRODUCT3));

        String productName = "banana";
        assertThatThrownBy(() -> productService.getProductByName(file, productName))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product with name %s not found".formatted(productName));
    }
}
