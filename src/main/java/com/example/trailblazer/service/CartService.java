package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.AddToCartRequest;
import com.example.trailblazer.dto.response.CartItemResponse;
import com.example.trailblazer.dto.response.CartResponse;
import com.example.trailblazer.entity.Cart;
import com.example.trailblazer.entity.CartItem;
import com.example.trailblazer.entity.Product;
import com.example.trailblazer.repository.CartRepository;
import com.example.trailblazer.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository
    ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public String addProductToCart(
            String email,
            AddToCartRequest request
    ) {
        Cart cart = cartRepository.findByCustomerEmail(email)
                                  .orElseThrow(() -> new RuntimeException("Cart not found for user: " + email));
        Product product = productRepository.findById(request.getProduct_id())
                                           .orElseThrow(() -> new RuntimeException("Product ID: " + request.getProduct_id() + " not found"));

        boolean isAlreadyInCart = false;

        for (CartItem item : cart.getItems()) {
            if (item.getProduct()
                    .getId()
                    .equals(product.getId())) {
                isAlreadyInCart = true;
                item.setQuantity(item.getQuantity() + request.getQuantity());
                break;
            }
        }

        if (!isAlreadyInCart) {
            CartItem newItem = new CartItem(cart, product, request.getQuantity());
            cart.getItems()
                .add(newItem);
        }

        cartRepository.save(cart);

        return "Product added to cart successfully!";
    }

    public CartResponse getUserCart(String email) {
        Cart cart = cartRepository.findByCustomerEmail(email)
                                  .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItemResponse> itemResponses = cart.getItems()
                                                   .stream()
                                                   .map(item -> new CartItemResponse(
                                                           item.getProduct()
                                                               .getId(), item.getProduct()
                                                                             .getName(), item.getQuantity(), item.getProduct()
                                                                                                                 .getPrice()
                                                   ))
                                                   .toList();

        return new CartResponse(cart.getId(), itemResponses);
    }
}
