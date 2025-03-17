package com.example.OrderService.events;

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
