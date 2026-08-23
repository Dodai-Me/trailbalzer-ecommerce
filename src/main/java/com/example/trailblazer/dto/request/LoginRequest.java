package com.example.trailblazer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Identifier (Email or Username) is required")
    private String identifier;

    @NotBlank(message = "Password is required")
    private String password;
}
