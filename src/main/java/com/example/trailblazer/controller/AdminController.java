package com.example.trailblazer.controller;

import com.example.trailblazer.dto.request.AdminSignUpRequest;
import com.example.trailblazer.dto.request.ChangePasswordRequest;
import com.example.trailblazer.dto.response.CustomerResponse;
import com.example.trailblazer.service.AdminService;
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
@RequestMapping("/api/admin")
@Tag(name = "Admin Management", description = "Secured endpoints for Admin operations")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(
            summary = "Register a new Admin",
            description = "Creates a new administrator account."
    )
    @PostMapping("/create-new-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull String> registerAdmin(
            @Valid @RequestBody
            AdminSignUpRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(adminService.registerAdmin(request));
    }

    @Operation(summary = "Edits Admin password", description = "Changes the admin password")
    @PatchMapping("/{username}/change-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull String> changeAdminPassword(
            @PathVariable String username,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        return ResponseEntity.ok(adminService.changeAdminPassword(username, request));
    }

    @Operation(summary = "Suspends Customer", description = "Suspends a customer's account")
    @PatchMapping("/{email}/suspend-customer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull String> suspendCustomer(@PathVariable String email) {
        return ResponseEntity.ok(adminService.suspendCustomer(email));
    }

    @Operation(summary = "Unsuspends Customer", description = "Unsuspends a customer's account")
    @PatchMapping("/{email}/unsuspend-customer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull String> unsuspendCustomer(@PathVariable String email) {
        return ResponseEntity.ok(adminService.unsuspendCustomer(email));
    }

    @Operation(summary = "Returns Customers", description = "Gets a list of all registered customers")
    @GetMapping("/get-customers")
    public ResponseEntity<@NonNull List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(adminService.getAllCustomers());
    }
}

