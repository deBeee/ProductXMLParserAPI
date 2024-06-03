package com.productxmlparserapi.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.productxmlparserapi.ProductXmlParserApiApplication;
import com.productxmlparserapi.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProductXmlParserApiApplication.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private MockMultipartFile validFile;
    private MockMultipartFile fileWithNoXmlContent;
    private MockMultipartFile fileWithIncorrectXmlContent;

    @BeforeEach
    void setUp() throws Exception {
        ClassPathResource validResource = new ClassPathResource("products-valid.xml");
        validFile = new MockMultipartFile(
                "file",
                "products-valid.xml",
                "application/xml",
                Files.readAllBytes(validResource.getFile().toPath())
        );

        ClassPathResource invalidResource = new ClassPathResource("products-invalid.xml");
        fileWithIncorrectXmlContent = new MockMultipartFile(
                "file",
                "product-invalid.xml",
                "application/xml",
                Files.readAllBytes(invalidResource.getFile().toPath())
        );

        fileWithNoXmlContent = new MockMultipartFile(
                "file",
                "product-invalid.xml",
                "application/xml",
                "I am not a xml content".getBytes()
        );
    }

    @Test
    @DisplayName("When XML file is valid, it should return the correct product count")
    void testGetProductCountWithValidFile() throws Exception {
        mockMvc.perform(multipart("/products/count").file(validFile))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "numberOfProducts": 3
                        }
                        """.trim()));
    }

    @Test
    @DisplayName("When XML file is valid, it should return all products")
    void testGetAllProductsWithValidFile() throws Exception {
        List<Product> expectedProducts = List.of(
                new Product(1, "apple", "fruit", "2303-E1A-G-M-W209B-VM", "FruitsAll", true),
                new Product(2, "orange", "fruit", "5603-J1A-G-M-W982F-PO", "FruitsAll", false),
                new Product(3, "glass", "dish", "9999-E7R-Q-M-K287B-YH", "HomeHome", true)
        );

        ResultActions resultActions = mockMvc.perform(multipart("/products/all").file(validFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(3)));

        for (int i = 0; i < expectedProducts.size(); i++) {
            Product expectedProduct = expectedProducts.get(i);
            resultActions
                    .andExpect(jsonPath("$.products[" + i + "].id", is(expectedProduct.id())))
                    .andExpect(jsonPath("$.products[" + i + "].name", is(expectedProduct.name())))
                    .andExpect(jsonPath("$.products[" + i + "].category", is(expectedProduct.category())))
                    .andExpect(jsonPath("$.products[" + i + "].partNumberNR", is(expectedProduct.partNumberNR())))
                    .andExpect(jsonPath("$.products[" + i + "].companyName", is(expectedProduct.companyName())))
                    .andExpect(jsonPath("$.products[" + i + "].active", is(expectedProduct.active())));
        }
    }

    @Test
    @DisplayName("When XML file is valid, it should return the correct product by name")
    void testGetProductByNameWithValidFile() throws Exception {
        mockMvc.perform(multipart("/products/find")
                        .file(validFile)
                        .param("name", "apple"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "product": {
                                "id": 1,
                                "name": "apple",
                                "category": "fruit",
                                "partNumberNR": "2303-E1A-G-M-W209B-VM",
                                "companyName": "FruitsAll",
                                "active": true
                            }
                        }
                                                """.trim()));
    }

    @Test
    @DisplayName("When product not found, it should return not found")
    void testProductNotFound() throws Exception {
        String productName = "banana";
        mockMvc.perform(multipart("/products/find")
                        .file(validFile)
                        .param("name", productName))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                            "status": 404,
                            "message": "Product with name %s not found",
                            "name": "%s"
                        }
                        """.trim().formatted(productName, productName)));
    }

    @Test
    @DisplayName("When XML file does not contain xml content, it should return bad request with proper message")
    void testFileWithNoXmlContent() throws Exception {
        String expectedResponseExceptionMessage =
                "Failed to read XML stream, please ensure the file is well-formed and try again";
        mockMvc.perform(multipart("/products/all").file(fileWithNoXmlContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponseExceptionMessage))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("When XML file contains xml content with incorrect format or structure, it should return bad request with proper message")
    void testFileWithIncorrectXmlContent() throws Exception {
        String expectedResponseExceptionMessage =
                "Failed to parse XML data, please check the file format correctness and ensure it matches the expected structure";
        mockMvc.perform(multipart("/products/all").file(fileWithIncorrectXmlContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponseExceptionMessage))
                .andExpect(jsonPath("$.status").value(400));
    }
}

