package com.example.trailblazer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Product() {

    }

    public Product(
            String name,
            String description,
            BigDecimal price
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}


