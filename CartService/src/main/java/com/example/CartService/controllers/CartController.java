package com.example.CartService.controllers;

import com.example.CartService.domain.Cart;
import com.example.CartService.dtos.requestDTO.CartItemRequestDTO;
import com.example.CartService.dtos.responseDTO.CartResponseDTO;
import com.example.CartService.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Get the cart details for a specific user.
     */
    @Operation(summary = "Get cart details", description = "Fetch the cart details for a specific user by user ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found or cart is empty")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long userId) {
        log.info("Fetching cart details for user ID: {}", userId);
        CartResponseDTO cartResponse = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartResponse);
    }

    /**
     * Add an item to the user's cart.
     */
    @Operation(summary = "Add item to cart", description = "Adds an item to the cart for a given user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/{userId}/items")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartItemRequestDTO cartRequestDTO) {
        log.info("Adding item to cart for user ID: {}", userId);
        Cart updatedCart = cartService.addToCart(userId, cartRequestDTO);
        log.info("Item added successfully for user ID: {}", userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
    }

    /**
     * Update the quantity of an item in the cart.
     */
    @Operation(summary = "Update cart item", description = "Updates the quantity of a specific item in the user's cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item or cart not found")
    })
    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartResponseDTO> updateCartItem(@PathVariable Long userId, @PathVariable Long itemId, @RequestParam int quantity) {
        log.info("Updating cart item ID: {} for user ID: {} with quantity: {}", itemId, userId, quantity);
        CartResponseDTO updatedCart = cartService.updateCartItem(userId, itemId, quantity);
        log.info("Cart updated successfully for user ID: {}", userId);
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Remove a specific item from the cart.
     */
    @Operation(summary = "Remove item from cart", description = "Removes a specific item from the user's cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found in cart")
    })
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartResponseDTO> removeItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        log.warn("Removing item ID: {} from cart for user ID: {}", itemId, userId);
        CartResponseDTO updatedCart = cartService.removeItemFromCart(userId, itemId);
        log.info("Item removed successfully for user ID: {}", userId);
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Clear all items from the user's cart.
     */
    @Operation(summary = "Clear cart", description = "Removes all items from the user's cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        log.warn("Clearing cart for user ID: {}", userId);
        cartService.clearCart(userId);
        log.info("Cart cleared successfully for user ID: {}", userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
