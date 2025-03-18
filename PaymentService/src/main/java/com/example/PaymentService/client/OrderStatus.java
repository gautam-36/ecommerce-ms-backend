package com.example.PaymentService.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public enum OrderStatus {
        PENDING_PAYMENT,
        CONFIRMED,
        SHIPPED,
        DELIVERED,
        CANCELLED,
        RETURN_REQUESTED,
        FAILED_PAYMENT,
        PAID

}
