package com.dbq.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {
  @Bean
  public CorsWebFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedMethod("*");
    config.addAllowedOrigin("http://localhost:4200");
    config.addAllowedOrigin("http://localhost:3000");
    config.addAllowedOrigin("https://myne.dbqportal.com");
    config.addAllowedHeader("*");
    config.setAllowCredentials(Boolean.valueOf(true));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
    source.registerCorsConfiguration("/**", config);
    return new CorsWebFilter((CorsConfigurationSource)source);
  }
}
