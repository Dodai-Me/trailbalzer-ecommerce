package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.ProductRequest;
import com.example.trailblazer.dto.request.ProductUpdateRequest;
import com.example.trailblazer.dto.response.ProductResponse;
import com.example.trailblazer.entity.Product;
import com.example.trailblazer.repository.CartItemRepository;
import com.example.trailblazer.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public ProductService(
            ProductRepository productRepository,
            CartItemRepository cartItemRepository
    ) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public String registerProduct(
            ProductRequest request
    ) {
        Product product = new Product(StringUtils.capitalize(request.getName()), StringUtils.capitalize(request.getDescription()), request.getPrice());

        productRepository.save(product);
        return "Product: " + product.getName() + " registered successfully!";
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> rawProduct = productRepository.findByIsDeletedIsFalse();


        return rawProduct.stream()
                         .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                         .toList();
    }

    public void updateProduct(
            Long id,
            ProductUpdateRequest request
    ) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Product with ID: " + id + " not found"));

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        productRepository.save(product);
    }

    @Transactional
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Product with ID: " + id + " not found"));
        cartItemRepository.deleteByProductId(id);
        product.setDeleted(true);
        productRepository.save(product);
        return "Product archived";
    }
}

