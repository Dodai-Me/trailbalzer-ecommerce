package com.example.trailblazer.entity;

import com.example.trailblazer.entity.contracts.PasswordUpdatable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "customers")
@Getter
@Setter
public class Customer implements PasswordUpdatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "ROLE_CUSTOMER";

    @Column(name = "is_suspended", nullable = false)
    private boolean isSuspended = false;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;


    public Customer() {

    }

    public Customer(
            String email,
            String password
    ) {
        this.email = email;
        this.password = password;
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email) && Objects.equals(password, customer.password);
    }

    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
