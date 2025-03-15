package com.example.InventoryService.service;


import com.example.InventoryService.domain.Inventory;

public interface InventoryService {


    boolean isProductAvailable(Long productId);

    Inventory updateInventory(Long productId, Integer quantity);

    Inventory getInventoryByProductId(Long productId);

    Inventory addInventory(Long productId, Integer quantity);
}
