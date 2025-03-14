package com.example.PaymentService.service;

import com.example.PaymentService.domain.Payment;
import com.example.PaymentService.enums.PaymentStatus;


public interface PaymentService {

    Payment processPayment(Long orderId, Long customerId, Double amount, String currency, String paymentMethod, String paymentProvider);
    Payment getPaymentDetails(Long paymentId);
    Payment updatePaymentStatus(Long paymentId, PaymentStatus status);
    Payment getPaymentByOrderId(Long orderId);


}
