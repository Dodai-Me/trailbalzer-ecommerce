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
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Suspends Customer", description = "Suspends a customer's account")
    @PatchMapping("/{email}/suspend-customer")
    public ResponseEntity<@NonNull String> suspendCustomer(@PathVariable String email) {
        return ResponseEntity.ok(adminService.suspendCustomer(email));
    }

    @Operation(summary = "Unsuspends Customer", description = "Unsuspends a customer's account")
    @PatchMapping("/{email}/unsuspend-customer")
    public ResponseEntity<@NonNull String> unsuspendCustomer(@PathVariable String email) {
        return ResponseEntity.ok(adminService.unsuspendCustomer(email));
    }

    @Operation(summary = "Returns Customers", description = "Gets a list of all registered customers")
    @GetMapping("/get-customers")
    public ResponseEntity<@NonNull List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(adminService.getAllCustomers());
    }
}

