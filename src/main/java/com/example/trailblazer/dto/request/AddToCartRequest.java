package com.example.trailblazer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    @NotNull(message = "Product ID is required")
    private Long product_id;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least one")
    private Integer quantity;
}
