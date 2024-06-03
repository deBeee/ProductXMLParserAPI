package com.productxmlparserapi.validation;


import com.productxmlparserapi.config.AppTestBeansConfig;
import com.productxmlparserapi.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestBeansConfig.class)
class ValidatorTest {

    @Autowired
    private Validator<Product> productValidator;

    @Test
    @DisplayName("When validation is successful")
    void testSuccessfulValidation() {
        Product correctProduct = new Product(
                1,
                "Product",
                "Category",
                "2303-E1A-G-M-W209B-VM",
                "Company",
                true
        );
        assertThat(Validator.validate(correctProduct, productValidator)).isTrue();
    }

    @Test
    @DisplayName("When validation is unsuccessful")
    void testUnsuccessfulValidation() {
        var productWithIncorrectPartNumberNR = new Product(
                1,
                "Product",
                "Category",
                "2303-E1A-G---M-W209B-VM",
                "Company",
                true
        );
        assertThat(Validator.validate(productWithIncorrectPartNumberNR, productValidator)).isFalse();
    }
}
