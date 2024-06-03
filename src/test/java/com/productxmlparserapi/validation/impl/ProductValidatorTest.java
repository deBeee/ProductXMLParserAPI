package com.productxmlparserapi.validation.impl;


import com.productxmlparserapi.config.AppTestBeansConfig;
import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestBeansConfig.class)
class ProductValidatorTest {

    @Autowired
    Validator<Product> productValidator;

    static Stream<Arguments> validationData() {
        return Stream.of(
                Arguments.of(
                        new Product(
                                1,
                                "Pro_duct",
                                "Category",
                                "2303-E1A-G-M-W209B-VM",
                                "Company",
                                true
                        ),
                        Map.of("name", "does not match regex: Pro_duct")
                ),
                Arguments.of(
                        new Product(
                                1,
                                "Product",
                                "Category239_",
                                "2303-E1A-G-M-W209B-VM",
                                "Company",
                                true
                        ),
                        Map.of("category", "does not match regex: Category239_")
                ),
                Arguments.of(
                        new Product(
                                1,
                                "Product",
                                "Category",
                                "23-03-E1A-G-M-W2-09-B-VM",
                                "Company",
                                true
                        ),
                        Map.of("partNumberNR", "does not match regex: 23-03-E1A-G-M-W2-09-B-VM")
                ),
                Arguments.of(
                        new Product(
                                1,
                                "Product",
                                "Category",
                                "2303-E1A-G-M-W209B-VM",
                                "Company 9393",
                                true
                        ),
                        Map.of("companyName", "does not match regex: Company 9393")
                ),
                Arguments.of(
                        new Product(
                                1,
                                "Pro_duct",
                                "Category",
                                "2303-E1A-G-M-W209B-VMABCDEFG",
                                "Company 9393",
                                true
                        ),
                        Map.ofEntries(
                                Map.entry("name", "does not match regex: Pro_duct"),
                                Map.entry("partNumberNR", "does not match regex: 2303-E1A-G-M-W209B-VMABCDEFG"),
                                Map.entry("companyName", "does not match regex: Company 9393")
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validationData")
    @DisplayName("When validation result is not correct")
    void testValidationErrors(Product product, Map<String, String> expectedErrors) {
        assertThat(productValidator.validate(product))
                .isEqualTo(expectedErrors);
    }
}
