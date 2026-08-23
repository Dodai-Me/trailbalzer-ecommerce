package com.example.trailblazer.controller;

import com.example.trailblazer.dto.request.AdminSignUpRequest;
import com.example.trailblazer.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<@NonNull String> registerAdmin(
            @Valid @RequestBody
            AdminSignUpRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(adminService.registerAdmin(request));
    }
}

