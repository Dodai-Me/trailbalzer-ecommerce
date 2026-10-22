package com.example.trailblazer.controller;

import com.example.trailblazer.dto.request.ChangePasswordRequest;
import com.example.trailblazer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer Management", description = "Secured endpoints for Customer operations")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Edits Customer password", description = "Changes the customer password")
    @PatchMapping("/change-password")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<@NonNull String> changeCustomerPassword(
            Principal principal,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        String loggedInCustomerEmail = principal.getName();
        return ResponseEntity.ok(customerService.changeCustomerPassword(loggedInCustomerEmail, request));
    }
}
