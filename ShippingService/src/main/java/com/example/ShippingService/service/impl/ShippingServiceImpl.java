package com.example.ShippingService.service.impl;

import com.example.ShippingService.repository.ShippingRepository;
import com.example.ShippingService.service.ShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    ShippingRepository shippingRepository;

}
