package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.ProductRequest;
import com.example.trailblazer.dto.response.ProductResponse;
import com.example.trailblazer.entity.Product;
import com.example.trailblazer.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String registerProduct(
            ProductRequest request
    ) {
        Product product = new Product(StringUtils.capitalize(request.getName()), StringUtils.capitalize(request.getDescription()), request.getPrice());

        productRepository.save(product);
        return "Product: " + product.getName() + " registered successfully!";
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> rawProduct = productRepository.findAll();

        return rawProduct.stream()
                         .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                         .toList();
    }
}

