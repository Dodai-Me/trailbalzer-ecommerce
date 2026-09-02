package com.example.trailblazer.service;

import com.example.trailblazer.dto.request.AddToCartRequest;
import com.example.trailblazer.dto.response.CartItemResponse;
import com.example.trailblazer.dto.response.CartResponse;
import com.example.trailblazer.entity.Cart;
import com.example.trailblazer.entity.CartItem;
import com.example.trailblazer.entity.Product;
import com.example.trailblazer.repository.CartItemRepository;
import com.example.trailblazer.repository.CartRepository;
import com.example.trailblazer.repository.ProductRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;


    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository,
            CartItemRepository cartItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
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

    @SneakyThrows
    public void removeProductFromCart(
            String email,
            Long id
    ) {
        Cart cart = cartRepository.findByCustomerEmail(email)
                                  .orElseThrow(() -> new RuntimeException("Cart not found for user: " + email));
        CartItem cartItem = cartItemRepository.findById(id)
                                              .orElseThrow(() -> new RuntimeException("Item: " + id + " not found"));
        if (!cartItem.getCart()
                     .getId()
                     .equals(cart.getId())) {
            throw new AccessDeniedException("You do not have permission to remove this item");
        }

        cartItemRepository.delete(cartItem);
    }

    public CartResponse getUserCart(String email) {
        Cart cart = cartRepository.findByCustomerEmail(email)
                                  .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItemResponse> itemResponses = cart.getItems()
                                                   .stream()
                                                   .map(item -> {
                                                       BigDecimal totalUnitPrice = item.getProduct()
                                                                                       .getPrice()
                                                                                       .multiply(BigDecimal.valueOf(item.getQuantity()));

                                                       return new CartItemResponse(
                                                               item.getProduct()
                                                                   .getId(), item.getProduct()
                                                                                 .getName(), item.getQuantity(), item.getProduct()
                                                                                                                     .getPrice(), totalUnitPrice);
                                                   })
                                                   .toList();

        BigDecimal grandTotal = itemResponses.stream()
                                             .map(CartItemResponse::getTotalUnitPrice)
                                             .reduce(BigDecimal.ZERO, BigDecimal::add);


        return new CartResponse(cart.getId(), grandTotal, itemResponses);
    }
}
