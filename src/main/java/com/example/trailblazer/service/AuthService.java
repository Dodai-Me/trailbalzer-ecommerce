package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.CustomerSignUpRequest;
import com.example.trailblazer.dto.response.JwtResponse;
import com.example.trailblazer.dto.request.LoginRequest;
import com.example.trailblazer.entity.Cart;
import com.example.trailblazer.entity.Customer;
import com.example.trailblazer.repository.CustomerRepository;
import com.example.trailblazer.security.CustomUserDetailsService;
import com.example.trailblazer.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            CustomerRepository customerRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getIdentifier(),
                        request.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getIdentifier());
        final String jwt = jwtService.generateToken(userDetails);
        return new JwtResponse(jwt);
    }

    public String registerCustomer(CustomerSignUpRequest request) {
        if (customerRepository.findByEmail(request.getEmail())
                              .isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        Customer customer = new Customer(request.getEmail(), passwordEncoder.encode(request.getPassword()));

        Cart cart = new Cart();

        cart.setCustomer(customer);
        customer.setCart(cart);

        customerRepository.save(customer);
        return "Customer Registered Successfully!";
    }
}
