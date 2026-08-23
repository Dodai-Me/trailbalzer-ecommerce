package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.AdminSignUpRequest;
import com.example.trailblazer.entity.Admin;
import com.example.trailblazer.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
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

}
