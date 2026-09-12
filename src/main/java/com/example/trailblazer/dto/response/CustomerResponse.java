package com.example.trailblazer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerResponse {
    private String email;
    private boolean isSuspended;
}
