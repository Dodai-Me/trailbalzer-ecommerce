package com.example.trailblazer.controller;

import com.example.trailblazer.dto.request.AdminSignUpRequest;
import com.example.trailblazer.dto.request.ChangePasswordRequest;
import com.example.trailblazer.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}

