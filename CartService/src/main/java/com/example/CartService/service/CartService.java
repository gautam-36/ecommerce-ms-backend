package com.example.CartService.service;

import com.example.CartService.domain.Cart;
import com.example.CartService.dtos.requestDTO.CartItemRequestDTO;
import com.example.CartService.dtos.responseDTO.CartResponseDTO;

public interface CartService {

    CartResponseDTO getCartByUserId(Long userId);
    Cart addToCart(Long userId, CartItemRequestDTO cartRequestDTO);
    CartResponseDTO updateCartItem(Long userId, Long itemId, int quantity);
    CartResponseDTO removeItemFromCart(Long userId, Long itemId);
    void clearCart(Long userId);
}
