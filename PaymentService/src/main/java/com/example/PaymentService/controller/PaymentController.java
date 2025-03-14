package com.example.PaymentService.controller;

import com.example.PaymentService.domain.Payment;
import com.example.PaymentService.enums.PaymentStatus;
import com.example.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

     @Autowired
     private PaymentService paymentService;

     @PostMapping("/process")
     public ResponseEntity<Payment> processPayment(
             @RequestParam Long orderId,
             @RequestParam Long customerId,
             @RequestParam Double amount,
             @RequestParam String currency,
             @RequestParam String paymentMethod,
             @RequestParam String paymentProvider) {

          return ResponseEntity.ok(paymentService.processPayment(orderId, customerId, amount, currency, paymentMethod, paymentProvider));
     }

     @GetMapping("/{paymentId}")
     public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long paymentId) {
          return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId));
     }

     @PutMapping("/{paymentId}/status")
     public ResponseEntity<Payment> updatePaymentStatus(
             @PathVariable Long paymentId,
             @RequestParam PaymentStatus status) {
          return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
     }

     @GetMapping("/order/{orderId}")
     public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable Long orderId) {
          return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
     }
}
