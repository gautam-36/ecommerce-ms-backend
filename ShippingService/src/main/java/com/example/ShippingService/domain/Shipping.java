package com.example.ShippingService.domain;

import com.example.ShippingService.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;

    private Long orderId;
    private Long customerId;
    private String shippingAddress;
    private String trackingNumber;
    private LocalDateTime shippedAt;
    private LocalDateTime estimatedDelivery;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;
}
