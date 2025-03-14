package com.example.CartService.service.impl;

import com.example.CartService.client.ProductFeignClient;
import com.example.CartService.client.ProductResponse;
import com.example.CartService.domain.Cart;
import com.example.CartService.domain.CartItem;
import com.example.CartService.dtos.requestDTO.CartItemRequestDTO;
import com.example.CartService.dtos.responseDTO.CartItemResponseDTO;
import com.example.CartService.dtos.responseDTO.CartResponseDTO;
import com.example.CartService.repository.CartItemRepository;
import com.example.CartService.repository.CartRepository;
import com.example.CartService.service.CartService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductFeignClient client;

    @Transactional
    @Override
    public Cart addToCart(Long userId, CartItemRequestDTO cartRequestDTO) {
        log.info("Adding product {} to cart for user {}", cartRequestDTO.getProductId(), userId);

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            log.info("No existing cart found for user {}. Creating a new cart.", userId);
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setItems(new ArrayList<>());
            return cartRepository.save(newCart);
        });

        Long productId = cartRequestDTO.getProductId();
        int quantity = cartRequestDTO.getQuantity();

        // Fetch product details from Product Service
        ProductResponse productResponse = client.getProductById(productId);
        log.info("Fetched product details: {}", productResponse);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            log.info("Product {} already in cart. Updated quantity to {}", productId, item.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            newItem.setPrice(productResponse.getPrice());
            newItem.setProductName(productResponse.getTitle());
            newItem.setCart(cart);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
            log.info("Added new product {} to cart with quantity {}", productId, quantity);
        }

        cartRepository.save(cart);
        return cart;
    }


    @Override
    public CartResponseDTO updateCartItem(Long userId, Long itemId, int quantity) {
        log.info("Updating cart item {} for user {} with new quantity {}", itemId, userId, quantity);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Cart not found for user ID: {}", userId);
                    return new RuntimeException("Cart not found for user ID: " + userId);
                });

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.error("Cart item not found with ID: {}", itemId);
                    return new RuntimeException("Cart item not found with ID: " + itemId);
                });

        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            log.error("Item {} does not belong to user's cart {}", itemId, userId);
            throw new RuntimeException("Item does not belong to this user's cart");
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        log.info("Updated cart item {} quantity to {}", itemId, quantity);

        return prepareCartResponse(cart);
    }

    @Override
    public CartResponseDTO removeItemFromCart(Long userId, Long itemId) {
        log.info("Removing item {} from cart for user {}", itemId, userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Cart not found for user ID: {}", userId);
                    return new RuntimeException("Cart not found for user ID: " + userId);
                });

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.error("Cart item not found with ID: {}", itemId);
                    return new RuntimeException("Cart item not found with ID: " + itemId);
                });

        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            log.error("Item {} does not belong to user's cart {}", itemId, userId);
            throw new RuntimeException("Item does not belong to this user's cart");
        }

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
        log.info("Successfully removed item {} from cart", itemId);

        return prepareCartResponse(cart);
    }



    @Override
    public void clearCart(Long userId) {
        log.info("Clearing cart for user {}", userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Cart not found for user ID: {}", userId);
                    return new RuntimeException("Cart not found for user ID: " + userId);
                });

        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("Cart cleared successfully for user {}", userId);
    }

    @Override
    public CartResponseDTO getCartByUserId(Long userId) {
        log.info("Fetching cart for user ID: {}", userId);
        Cart cart =  cartRepository.findByUserId(userId).orElse(null);
        // prepare a Response
        CartResponseDTO responseDTO = new CartResponseDTO();
        if(Objects.nonNull(cart) && !cart.getItems().isEmpty()){
            log.info("Cart found for user ID: {} with {} items", userId, cart.getItems().size());

             responseDTO.setCartId(cart.getCartId());
             responseDTO.setUserId(cart.getUserId());

            List<CartItemResponseDTO> itemResponseDTOS = getCartItemResponseDTOS(cart);
            responseDTO.setItems(itemResponseDTOS);
            Double totalPrice = calculateTotalCartPrice(itemResponseDTOS);
            responseDTO.setTotalCartPrice(totalPrice);

        }else{
            log.info("No cart found or cart is empty for user ID: {}", userId);
        }
        return responseDTO;
    }

    // helper methods to prepare response dtos
    private CartResponseDTO prepareCartResponse(Cart cart) {
        CartResponseDTO responseDTO = new CartResponseDTO();
        if (Objects.nonNull(cart) && !cart.getItems().isEmpty()) {
            responseDTO.setCartId(cart.getCartId());
            responseDTO.setUserId(cart.getUserId());
            List<CartItemResponseDTO> itemResponseDTOS = getCartItemResponseDTOS(cart);
            responseDTO.setItems(itemResponseDTOS);
            responseDTO.setTotalCartPrice(calculateTotalCartPrice(itemResponseDTOS));
            log.info("Prepared cart response for cart ID {}", cart.getCartId());
        } else {
            log.info("Cart is empty or does not exist");
        }
        return responseDTO;
    }

    // helper method for return List of cartItem Responses from Carts
    private static List<CartItemResponseDTO> getCartItemResponseDTOS(Cart cart) {
        log.info("Preparing cart items response for cart ID: {}", cart.getCartId());
        List<CartItem> items = cart.getItems();
        List<CartItemResponseDTO> itemResponseDTOS = new ArrayList<>();
        for(CartItem cartItem:items){
            CartItemResponseDTO itemResponseDTO = new CartItemResponseDTO();
            itemResponseDTO.setQuantity(cartItem.getQuantity());
                 itemResponseDTO.setPrice(cartItem.getPrice());
            itemResponseDTO.setProductId(cartItem.getProductId());
                 itemResponseDTO.setProductName(cartItem.getProductName());
            itemResponseDTOS.add(itemResponseDTO);
            log.info("Added item to response - Product ID: {}, Name: {}, Quantity: {}, Price: {}",
                    cartItem.getProductId(), cartItem.getProductName(), cartItem.getQuantity(), cartItem.getPrice());
        }
        log.info("Total {} cart items processed for cart ID: {}", itemResponseDTOS.size(), cart.getCartId());
        return itemResponseDTOS;
    }

    // helper methods to calculate total Price of cart
    private static Double calculateTotalCartPrice(List<CartItemResponseDTO> itemResponseDTO){
        log.info("Calculating total cart price...");
        Double totalPrice = 0.0;
        for(CartItemResponseDTO items: itemResponseDTO){
            totalPrice+=items.getPrice()*items.getQuantity();
        }
        log.info("Total cart price calculated: {}", totalPrice);
        return totalPrice;
    }


}
