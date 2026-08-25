package com.example.trailblazer.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductUpdateRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
