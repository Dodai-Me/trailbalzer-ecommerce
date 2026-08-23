package com.example.trailblazer.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSignUpRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Not a valid email")
    @Schema(example = "lionelmessi@gmail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{12,}$",
            message = "Password must be at least 12 characters long, contain at least one number, one uppercase letter, and one special character."
    )
    @Schema(example = "12 characters with at least a special character, number, capital letter")
    private String password;
}
