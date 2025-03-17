package com.example.ShippingService.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private Long orderId;
    private Long customerId;
    private String shippingAddress;
}
