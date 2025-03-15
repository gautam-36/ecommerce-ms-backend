package com.example.PaymentService.controller;

import com.example.PaymentService.domain.Payment;
import com.example.PaymentService.enums.PaymentStatus;
import com.example.PaymentService.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment API", description = "Endpoints for processing and managing payments")
public class PaymentController {

     @Autowired
     private PaymentService paymentService;

     @PostMapping("/process")
     @Operation(summary = "Process a payment", description = "Processes a new payment for an order using the specified payment method and provider.")
     public ResponseEntity<?> processPayment(
             @Parameter(description = "ID of the order being paid for") @RequestParam Long orderId,
             @Parameter(description = "ID of the customer making the payment") @RequestParam Long customerId,
             @Parameter(description = "Total payment amount") @RequestParam Double amount,
             @Parameter(description = "Currency of the payment (e.g., USD, EUR)") @RequestParam String currency,
             @Parameter(description = "Payment method (e.g., Credit Card, PayPal)") @RequestParam String paymentMethod,
             @Parameter(description = "Payment provider handling the transaction (e.g., Stripe, PayPal)") @RequestParam String paymentProvider) {
          try {
               Payment payment = paymentService.processPayment(orderId, customerId, amount, currency, paymentMethod, paymentProvider);
               return ResponseEntity.ok(payment);
          } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body("Error processing payment: " + e.getMessage());
          }
     }

     @GetMapping("/{paymentId}")
     @Operation(summary = "Get payment details", description = "Retrieves details of a specific payment by its ID.")
     public ResponseEntity<?> getPaymentDetails(
             @Parameter(description = "ID of the payment to retrieve") @PathVariable Long paymentId) {
          try {
               Payment payment = paymentService.getPaymentDetails(paymentId);
               return ResponseEntity.ok(payment);
          } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("message: " + e.getMessage());
          }
     }

     @PutMapping("/{paymentId}/status")
     @Operation(summary = "Update payment status", description = "Updates the status of a payment (e.g., SUCCESS, FAILED, PENDING).")
     public ResponseEntity<?> updatePaymentStatus(
             @Parameter(description = "ID of the payment to update") @PathVariable Long paymentId,
             @Parameter(description = "New payment status") @RequestParam PaymentStatus status) {
          try {
               Payment updatedPayment = paymentService.updatePaymentStatus(paymentId, status);
               return ResponseEntity.ok(updatedPayment);
          } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Error updating payment status: " + e.getMessage());
          }
     }

     @GetMapping("/order/{orderId}")
     @Operation(summary = "Get payment by order ID", description = "Retrieves the payment details associated with a specific order.")
     public ResponseEntity<?> getPaymentByOrderId(
             @Parameter(description = "ID of the order to fetch payment details for") @PathVariable Long orderId) {
          try {
               Payment payment = paymentService.getPaymentByOrderId(orderId);
               return ResponseEntity.ok(payment);
          } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("message: " + e.getMessage());
          }
     }
}
