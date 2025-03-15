package com.example.InventoryService.service.impl;

import com.example.InventoryService.client.CartFiegnClient;
import com.example.InventoryService.domain.Inventory;
import com.example.InventoryService.repository.InventoryRepository;
import com.example.InventoryService.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    CartFiegnClient client;


    @Override
    public Inventory addInventory(Long productId, Integer quantity) {
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product ID: " + productId));
    }

    @Override
    public Inventory updateInventory(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product ID: " + productId));

        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public boolean isProductAvailable(Long productId) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        return inventory.map(inv -> inv.getQuantity() > 0).orElse(false);
    }
}
