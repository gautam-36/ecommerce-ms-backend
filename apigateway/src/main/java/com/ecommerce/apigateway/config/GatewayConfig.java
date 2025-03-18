package com.ecommerce.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${discovery.server.url}")
    private String discoveryServerUrl;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${cart.service.url}")
    private String cartServiceUrl;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @Value("${shipping.service.url}")
    private String shippingServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Swagger UI Routes
                .route("swagger-ui", r -> r.path("/swagger-ui.html")
                        .filters(f -> f.rewritePath("/swagger-ui.html", "/swagger-ui/index.html"))
                        .uri("lb://api-gateway"))

                // Swagger Documentation for product, cart, order, payment, and shipping services
                .route("product-service-docs", r -> r.path("/product-service/api-docs")
                        .uri("lb://product-service"))
                .route("cart-service-docs", r -> r.path("/cart-service/api-docs")
                        .uri("lb://cart-service"))
                .route("order-service-docs", r -> r.path("/order-service/api-docs")
                        .uri("lb://order-service"))
                .route("payment-service-docs", r -> r.path("/payment-service/api-docs")
                        .uri("lb://payment-service"))
                .route("shipping-service-docs", r -> r.path("/shipping-service/api-docs")
                        .uri("lb://shipping-service"))

                // Route for the API services
                .route("product-service", r -> r.path("/product-service/**")
                        .uri(productServiceUrl))
                .route("cart-service", r -> r.path("/cart-service/**")
                        .uri(cartServiceUrl))
                .route("order-service", r -> r.path("/order-service/**")
                        .uri(orderServiceUrl))
                .route("payment-service", r -> r.path("/payment-service/**")
                        .uri(paymentServiceUrl))
                .route("shipping-service", r -> r.path("/shipping-service/**")
                        .uri(shippingServiceUrl))

                // Route for Discovery Server
                .route("discovery-server", r -> r.path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri(discoveryServerUrl))
                .route("discovery-server-static", r -> r.path("/eureka/**")
                        .filters(f -> f.setPath("/"))
                        .uri(discoveryServerUrl))
                .build();
    }
}
