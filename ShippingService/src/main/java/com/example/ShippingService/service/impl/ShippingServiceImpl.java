package com.example.ShippingService.service.impl;

import com.example.ShippingService.domain.Shipping;
import com.example.ShippingService.enums.ShippingStatus;
import com.example.ShippingService.events.OrderEvent;
import com.example.ShippingService.repository.ShippingRepository;
import com.example.ShippingService.service.ShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    ShippingRepository shippingRepository;

    @Override
    @KafkaListener(topics = "order-created-topic", groupId = "shipping-group")
    public Shipping createShipment(OrderEvent event) {
        log.info("Received order event: {}", event);

        Shipping shipping = Shipping.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .shippingAddress(event.getShippingAddress())
                .status(ShippingStatus.PENDING)
                .shippedAt(LocalDateTime.now())
                .estimatedDelivery(LocalDateTime.now().plusDays(5))
                .trackingNumber("TRK" + System.currentTimeMillis())
                .build();

        return shippingRepository.save(shipping);
    }

    @Override
    public Shipping getShipmentById(Long shippingId) {
        return shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Shipping not found with ID: " + shippingId));
    }

    @Override
    public Shipping getShipmentByOrderId(Long orderId) {
        return shippingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Shipping not found for Order ID: " + orderId));
    }

    @Override
    public Shipping updateShippingStatus(Long shippingId, ShippingStatus status) {
        Shipping shipping = getShipmentById(shippingId);
        shipping.setStatus(status);
        return shippingRepository.save(shipping);
    }

    @Override
    public List<Shipping> getAllShipments() {
        return shippingRepository.findAll();
    }
}
