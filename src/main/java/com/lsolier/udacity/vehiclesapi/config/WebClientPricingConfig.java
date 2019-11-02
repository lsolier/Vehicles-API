package com.lsolier.udacity.vehiclesapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientPricingConfig {

  @Value("${external-services.pricing.endpoint}")
  private String endpoint;

  /**
   * Web Client for the pricing API
   * @return created pricing endpoint
   */
  @Bean(name="pricing")
  public WebClient webClientPricing() {
    return WebClient.create(endpoint);
  }
}
