package com.example.PaymentService.service.impl;

import com.example.PaymentService.client.OrderFiegnClient;
import com.example.PaymentService.client.OrderStatus;
import com.example.PaymentService.domain.Payment;
import com.example.PaymentService.enums.PaymentStatus;
import com.example.PaymentService.repository.PaymentRepository;
import com.example.PaymentService.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderFiegnClient client;


    @Override
    public Payment processPayment(Long orderId, Long customerId, Double amount, String currency, String paymentMethod, String paymentProvider) {

        // TODO: add check that order exists or not for the given orderId
        Payment savedPayment = makePayment(orderId, customerId, amount, currency, paymentMethod, paymentProvider);
        log.info("Payment saved with ID: {} and Transaction ID: {}", savedPayment.getPaymentId(), savedPayment.getTransactionId());

        // TODO: call an event here
        if(Objects.nonNull(savedPayment) && savedPayment.getStatus().equals(PaymentStatus.SUCCESS)) {
            log.info("call here an event like change the status of order");
            client.updateOrderStatus(orderId, OrderStatus.CONFIRMED);
        }else{
            log.info("call here for failed payment");
            client.updateOrderStatus(orderId, OrderStatus.FAILED_PAYMENT);

        }

        return savedPayment;
    }

    private Payment makePayment(Long orderId, Long customerId, Double amount, String currency, String paymentMethod, String paymentProvider) {

        log.info("Processing payment for order ID: {}", orderId);
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setCustomerId(customerId);
        payment.setAmount(amount);
        payment.setCurrency(currency);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentProvider(paymentProvider);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(generateTransactionId());
        payment.setPaymentReference(generatePaymentReference());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment;
    }

    @Override
    public Payment getPaymentDetails(Long paymentId) {
        log.info("Fetching payment details for ID: {}", paymentId);
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {
        log.info("Updating payment status for ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        log.info("Fetching payment for order ID: {}", orderId);
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order ID: " + orderId));
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }

    private String generatePaymentReference() {
        return "REF-" + UUID.randomUUID().toString();
    }


}
