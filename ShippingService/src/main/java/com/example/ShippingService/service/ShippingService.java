package com.example.ShippingService.service;


import com.example.ShippingService.domain.Shipping;
import com.example.ShippingService.enums.ShippingStatus;
import com.example.ShippingService.events.OrderEvent;

import java.util.List;

public interface ShippingService {


    Shipping createShipment(OrderEvent event);

    Shipping getShipmentById(Long shippingId);

    Shipping getShipmentByOrderId(Long orderId);

    Shipping updateShippingStatus(Long shippingId, ShippingStatus status);

    List<Shipping> getAllShipments();
}
