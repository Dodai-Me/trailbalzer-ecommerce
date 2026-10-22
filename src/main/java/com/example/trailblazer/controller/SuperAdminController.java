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
@RequestMapping("/api/super-admin")
@Tag(name = "Super Admin Management", description = "Secured endpoints for Super Admin operations")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {
    private final AdminService adminService;

    public SuperAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(
            summary = "Register a new Admin",
            description = "Creates a new administrator account."
    )
    @PostMapping("/create-new-admin")
    public ResponseEntity<@NonNull String> registerAdmin(
            @Valid @RequestBody
            AdminSignUpRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(adminService.registerAdmin(request));
    }

    @Operation(summary = "Edits Admin password", description = "Changes the admin password")
    @PatchMapping("/{username}/change-password")
    public ResponseEntity<@NonNull String> changeAdminPassword(
            @PathVariable String username,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        return ResponseEntity.ok(adminService.changeAdminPassword(username, request));
    }
}
