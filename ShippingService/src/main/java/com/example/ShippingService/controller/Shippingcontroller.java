package com.example.ShippingService.controller;

import com.example.ShippingService.service.ShippingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Shipping API", description = "Endpoints for managing product inventory")
public class Shippingcontroller {

     @Autowired
     private ShippingService shippingService;


}
