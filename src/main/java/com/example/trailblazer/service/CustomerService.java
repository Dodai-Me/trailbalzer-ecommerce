package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.ChangePasswordRequest;
import com.example.trailblazer.entity.Customer;
import com.example.trailblazer.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String changeCustomerPassword(
            String email,
            ChangePasswordRequest request
    ) {
        Customer customer = customerRepository.findByEmail(email)
                                              .orElseThrow(() -> new RuntimeException("Admin with ID: " + email + " not found"));
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customerRepository.save(customer);
        return "Password changed successfully!";
    }
}
