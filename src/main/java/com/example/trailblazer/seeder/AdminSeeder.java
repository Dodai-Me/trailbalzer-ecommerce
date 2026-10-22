package com.example.trailblazer.seeder;

import com.example.trailblazer.entity.Admin;
import com.example.trailblazer.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default.username}")
    private String defaultAdminUsername;

    @Value("${admin.default.password}")
    private String defaultAdminPassword;

    public AdminSeeder(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin(defaultAdminUsername, passwordEncoder.encode(defaultAdminPassword));
            admin.setRole("ROLE_SUPER_ADMIN");
            adminRepository.save(admin);
            System.out.println("No admins found. Default Admin created successfully.");
        }
    }
}
