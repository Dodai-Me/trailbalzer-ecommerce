package com.example.trailblazer.controller;

import com.example.trailblazer.dto.request.ProductRequest;
import com.example.trailblazer.dto.response.ProductResponse;
import com.example.trailblazer.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@Tag(name = "Product Management", description = "Secured endpoints for Product operations")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Register a new product", description = "Creates a new product")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull String> registerProduct(
            @Valid @RequestBody
            ProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(productService.registerProduct(request));
    }

    @Operation(summary = "Returns all products", description = "Finds all created products")
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<@NonNull List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
