package com.productxmlparserapi.validation.impl;

import com.productxmlparserapi.model.Product;
import com.productxmlparserapi.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductValidator implements Validator<Product> {

    private static final String REGEX = "^[a-zA-Z ]+$";
    private static final String PARTNUMBERNR_REGEX = "^\\d{4}-[A-Z]\\d([A-Z]-){3}[A-Z]\\d{3}[A-Z]-[A-Z]{2}$";

    @Override
    public Map<String, String> validate(Product product) {
        Map<String, String> errors = new HashMap<>();

        if (doesNotMatchRegex(product.name(), REGEX)) {
            errors.put("name", "does not match regex: " + product.name());
        }

        if (doesNotMatchRegex(product.category(), REGEX)) {
            errors.put("category", "does not match regex: " + product.category());
        }

        if (doesNotMatchRegex(product.partNumberNR(), PARTNUMBERNR_REGEX)) {
            errors.put("partNumberNR", "does not match regex: " + product.partNumberNR());
        }

        if (doesNotMatchRegex(product.companyName(), REGEX)) {
            errors.put("companyName", "does not match regex: " + product.companyName());
        }
        return errors;
    }

    private static boolean doesNotMatchRegex(String text, String regex) {
        return text == null || !text.matches(regex);
    }
}
