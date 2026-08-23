package com.example.trailblazer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CartItemResponse {
    private final Long productId;
    private final String productName;
    private final Integer quantity;
    private final BigDecimal unitPrice;
}
