package com.example.trailblazer.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductUpdateRequest {
    @NotBlank(message = "Product name required")
    private String name;

    private String description;

    @NotNull(message = "Price required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
}
