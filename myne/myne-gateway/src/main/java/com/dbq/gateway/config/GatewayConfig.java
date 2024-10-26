package com.dbq.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dbq.gateway.filter.JwtAuthenticationFilter;





@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("myne-user-service", r -> r.path("/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://myne-user-service"))

                .route("myne-auth-service", r -> r.path("/v1/auth/**")
                        .uri("lb://myne-auth-service"))
                
                .route("myne-s3-service", r -> r.path("/v1/s3-storage/**")
                		.filters(f -> f.filter(filter))
                        .uri("lb://myne-s3-service"))
                
                .route("myne-post-service", r -> r.path("/v1/post/**")
                		.filters(f -> f.filter(filter))
                        .uri("lb://myne-post-service"))
                
                .route("myne-notify-service", r -> r.path("/v1/notify/**")
                		.filters(f -> f.filter(filter))
                        .uri("lb://myne-notify-service"))
                
//                .route("file-storage", r -> r.path("/v1/file-storage/**")
//                        .filters(f -> f.filter(filter))
//                        .uri("lb://file-storage"))
                .build();
    }
    
    
}