package com.example.InventoryService.controller;

import com.example.InventoryService.domain.Inventory;
import com.example.InventoryService.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory API", description = "Endpoints for managing product inventory")
public class InventoryController {

     @Autowired
     private InventoryService inventoryService;

     @PostMapping
     @Operation(summary = "Add Inventory", description = "Adds new inventory stock for a product.")
     public ResponseEntity<?> addInventory(
             @Parameter(description = "Product ID") @RequestParam Long productId,
             @Parameter(description = "Stock quantity to add") @RequestParam Integer quantity) {
          try {
               Inventory inventory = inventoryService.addInventory(productId, quantity);
               return ResponseEntity.ok(inventory);
          } catch (RuntimeException e) {
               return ResponseEntity.status(400).body(e.getMessage());
          }
     }

     @GetMapping("/{productId}")
     @Operation(summary = "Get Inventory by Product ID", description = "Retrieves stock details of a specific product.")
     public ResponseEntity<?> getInventoryByProductId(
             @Parameter(description = "Product ID") @PathVariable Long productId) {
          try {
               Inventory inventory = inventoryService.getInventoryByProductId(productId);
               return ResponseEntity.ok(inventory);
          } catch (RuntimeException e) {
               return ResponseEntity.status(404).body(e.getMessage());
          }
     }

     @PutMapping("/{productId}/update")
     @Operation(summary = "Update Inventory Stock", description = "Updates stock levels for a product.")
     public ResponseEntity<?> updateInventoryStock(
             @Parameter(description = "Product ID") @PathVariable Long productId,
             @Parameter(description = "New stock quantity") @RequestParam Integer quantity) {
          try {
               Inventory inventory = inventoryService.updateInventory(productId, quantity);
               return ResponseEntity.ok(inventory);
          } catch (RuntimeException e) {
               return ResponseEntity.status(400).body(e.getMessage());
          }
     }

     @GetMapping("/{productId}/availability")
     @Operation(summary = "Check Stock Availability", description = "Checks if a product is in stock.")
     public ResponseEntity<?> checkStockAvailability(
             @Parameter(description = "Product ID") @PathVariable Long productId) {
          try {
               boolean isAvailable = inventoryService.isProductAvailable(productId);
               return ResponseEntity.ok(isAvailable);
          } catch (RuntimeException e) {
               return ResponseEntity.status(400).body(e.getMessage());
          }
     }
}
