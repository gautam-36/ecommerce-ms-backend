package com.example.OrderService.service;

import com.example.OrderService.domain.Cart;
import com.example.OrderService.dtos.requestDTO.CartItemRequestDTO;
import com.example.OrderService.dtos.responseDTO.CartResponseDTO;
import jakarta.transaction.Transactional;

public interface CartService {

    CartResponseDTO getCartByUserId(Long userId);
    Cart addToCart(Long userId, CartItemRequestDTO cartRequestDTO);
    CartResponseDTO updateCartItem(Long userId, Long itemId, int quantity);
    CartResponseDTO removeItemFromCart(Long userId, Long itemId);
    void clearCart(Long userId);
}
