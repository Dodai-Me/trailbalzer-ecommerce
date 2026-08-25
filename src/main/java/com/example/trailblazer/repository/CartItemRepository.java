package com.example.trailblazer.repository;

import com.example.trailblazer.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
