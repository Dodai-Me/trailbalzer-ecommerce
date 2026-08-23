package com.example.trailblazer.controller;

import com.example.trailblazer.dto.request.CustomerSignUpRequest;
import com.example.trailblazer.dto.request.LoginRequest;
import com.example.trailblazer.security.LoginRateLimiter;
import com.example.trailblazer.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Authentication Management", description = "Secured endpoints for Admin operations")
public class AuthController {

    private final AuthService authService;
    private final LoginRateLimiter rateLimiter;

    public AuthController(
            AuthService authService,
            LoginRateLimiter rateLimiter
    ) {
        this.authService = authService;
        this.rateLimiter = rateLimiter;
    }

    @Operation(
            summary = "Authenticate a user",
            description = "Validates the user's credentials (email/username and password)."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {

        String clientIp = httpRequest.getRemoteAddr();

        if (!rateLimiter.tryConsume(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                                 .body("Too many login requests. Try again in 1 minute");
        }
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            summary = "Register a new Customer",
            description = "Creates a new customer account. Requires a valid email and password."
    )
    @PostMapping("/signup/customer")
    public ResponseEntity<@NonNull String> registerCustomer(
            @Valid @RequestBody
            CustomerSignUpRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(authService.registerCustomer(request));
    }

}
