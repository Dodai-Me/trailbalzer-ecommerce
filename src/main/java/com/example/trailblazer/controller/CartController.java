package com.example.trailblazer.controller;

import com.example.trailblazer.dto.request.AddToCartRequest;
import com.example.trailblazer.dto.response.CartResponse;
import com.example.trailblazer.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management", description = "Secured endpoints for Cart operations")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Register a product to cart", description = "Adds a product to cart")
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<@NonNull String> addProductToCart(
            Principal principal,
            @Valid @RequestBody AddToCartRequest request
    ) {
        String email = principal.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(cartService.addProductToCart(email, request));
    }

    @Operation(summary = "Returns a user's cart", description = "Shows products of a user's cart")
    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<@NonNull CartResponse> getUserCart(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(cartService.getUserCart(email));
    }

    @Operation(summary = "Deletes an item from cart", description = "Removes an item from a user's cart")
    @DeleteMapping("/items/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void removeProductFromCart(
            Principal principal,
            @PathVariable Long id
    ) {
        String email = principal.getName();
        cartService.removeProductFromCart(email, id);
        ResponseEntity.noContent()
                      .build();
    }
}
