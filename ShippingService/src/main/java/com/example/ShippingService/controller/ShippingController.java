package com.example.ShippingService.controller;

import com.example.ShippingService.domain.Shipping;
import com.example.ShippingService.enums.ShippingStatus;
import com.example.ShippingService.events.OrderEvent;
import com.example.ShippingService.service.ShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
@Tag(name = "Shipping API", description = "Endpoints for managing shipments")
@Slf4j
public class ShippingController {

     @Autowired
     private ShippingService shippingService;

     @PostMapping("/create")
     @Operation(summary = "Create a shipment", description = "Creates a shipment for an order and assigns tracking details.")
     public ResponseEntity<Shipping> createShipment(@RequestBody OrderEvent event) {
          try {
               return ResponseEntity.ok(shippingService.createShipment(event));
          } catch (RuntimeException e) {
               log.error("Error creating shipment: {}", e.getMessage());
               return ResponseEntity.badRequest().body(null);
          }
     }

     @GetMapping("/{shippingId}")
     @Operation(summary = "Get shipment details", description = "Fetches shipment details by shipment ID.")
     public ResponseEntity<Shipping> getShipmentById(
             @Parameter(description = "ID of the shipment") @PathVariable Long shippingId) {
          try {
               return ResponseEntity.ok(shippingService.getShipmentById(shippingId));
          } catch (RuntimeException e) {
               log.error("Error fetching shipment: {}", e.getMessage());
               return ResponseEntity.badRequest().body(null);
          }
     }

     @GetMapping("/order/{orderId}")
     @Operation(summary = "Get shipment by order ID", description = "Fetches the shipment details for a specific order.")
     public ResponseEntity<Shipping> getShipmentByOrderId(
             @Parameter(description = "Order ID to fetch shipment") @PathVariable Long orderId) {
          try {
               return ResponseEntity.ok(shippingService.getShipmentByOrderId(orderId));
          } catch (RuntimeException e) {
               log.error("Error fetching shipment for order: {}", e.getMessage());
               return ResponseEntity.badRequest().body(null);
          }
     }

     @PutMapping("/{shippingId}/status")
     @Operation(summary = "Update shipment status", description = "Updates the status of a shipment (e.g., SHIPPED, DELIVERED, CANCELLED).")
     public ResponseEntity<Shipping> updateShippingStatus(
             @Parameter(description = "Shipment ID to update") @PathVariable Long shippingId,
             @Parameter(description = "New shipment status") @RequestParam ShippingStatus status) {
          try {
               return ResponseEntity.ok(shippingService.updateShippingStatus(shippingId, status));
          } catch (RuntimeException e) {
               log.error("Error updating shipment status: {}", e.getMessage());
               return ResponseEntity.badRequest().body(null);
          }
     }

     @GetMapping
     @Operation(summary = "Get all shipments", description = "Retrieves a list of all shipments.")
     public ResponseEntity<List<Shipping>> getAllShipments() {
          return ResponseEntity.ok(shippingService.getAllShipments());
     }
}
