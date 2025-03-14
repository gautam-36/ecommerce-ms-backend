package com.example.PaymentService.domain;
import com.example.PaymentService.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long orderId;  // Reference to Order Service
    private Long customerId; // Optional: If tracking customers

    private Double amount;
    private String currency; // USD, INR, EUR, etc.

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentMethod; // CreditCard, PayPal, UPI, etc.
    private String paymentProvider; // Stripe, PayPal, Razorpay

    private String transactionId; // From external payment provider
    private String paymentReference; // Unique identifier for the transaction
    private String failureReason; // Store reason for failed payments (if any)

    private boolean isRefunded;
    private Double refundAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

