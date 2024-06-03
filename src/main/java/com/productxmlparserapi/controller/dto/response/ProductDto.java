package com.productxmlparserapi.controller.dto.response;

import lombok.Builder;

@Builder
public record ProductDto(
        int id,
        String name,
        String category,
        String partNumberNR,
        String companyName,
        boolean active
) {
}
