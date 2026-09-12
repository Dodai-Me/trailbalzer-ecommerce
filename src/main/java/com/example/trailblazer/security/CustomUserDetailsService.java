package com.example.trailblazer.security;

import com.example.trailblazer.entity.Admin;
import com.example.trailblazer.entity.Customer;
import com.example.trailblazer.repository.AdminRepository;
import com.example.trailblazer.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(
            AdminRepository adminRepository,
            CustomerRepository customerRepository
    ) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        Optional<Admin> optionalAdmin = adminRepository.findByUsername(identifier);

        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            return User.builder()
                       .username(admin.getUsername())
                       .password(admin.getPassword())
                       .authorities(admin.getRole())
                       .build();
        }
        
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(identifier);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return User.builder()
                       .username(customer.getEmail())
                       .password(customer.getPassword())
                       .authorities(customer.getRole())
                       .accountLocked(customer.isSuspended())
                       .build();
        }

        throw new UsernameNotFoundException("User not found with identifier: " + identifier);
    }
}
