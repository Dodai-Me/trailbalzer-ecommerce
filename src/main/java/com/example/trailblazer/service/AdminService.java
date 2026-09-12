package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.AdminSignUpRequest;
import com.example.trailblazer.dto.request.ChangePasswordRequest;
import com.example.trailblazer.dto.response.CartResponse;
import com.example.trailblazer.dto.response.CustomerResponse;
import com.example.trailblazer.entity.Admin;
import com.example.trailblazer.entity.Customer;
import com.example.trailblazer.repository.AdminRepository;
import com.example.trailblazer.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public AdminService(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder,
            CustomerRepository customerRepository
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    public String registerAdmin(AdminSignUpRequest request) {
        if (adminRepository.findByUsername(request.getUsername())
                           .isPresent()) {
            throw new RuntimeException("Username has already been registered");
        }

        Admin admin = new Admin(request.getUsername(), passwordEncoder.encode(request.getPassword()));

        adminRepository.save(admin);
        return "Admin Registered Successfully!";
    }

    public String changeAdminPassword(
            String username,
            ChangePasswordRequest request
    ) {
        Admin admin = adminRepository.findByUsername(username)
                                     .orElseThrow(() -> new RuntimeException("Admin with ID: " + username + " not found"));
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        adminRepository.save(admin);
        return "Password changed successfully!";
    }

    @Transactional
    public String suspendCustomer(String email) {
        Customer customer = customerRepository.findByEmail(email)
                                              .orElseThrow(() -> new RuntimeException("Customer with email: " + email + " not found"));

        if (customer.isSuspended()) {
            return "Customer is already suspended.";
        }
        customer.setSuspended(true);
        customerRepository.save(customer);
        return "Customer with email: " + email + " has been suspended.";
    }

    @Transactional
    public String unsuspendCustomer(String email) {
        Customer customer = customerRepository.findByEmail(email)
                                              .orElseThrow(() -> new RuntimeException("Customer with email: " + email + " not found"));

        if (!customer.isSuspended()) {
            return "Customer is not suspended.";
        }
        customer.setSuspended(false);
        customerRepository.save(customer);
        return "Customer with email: " + email + " has been unsuspended.";
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> rawCustomer = customerRepository.findAll();

        return rawCustomer.stream()
                          .map(customer -> new CustomerResponse(customer.getEmail(), customer.isSuspended()))
                          .toList();
    }
}
