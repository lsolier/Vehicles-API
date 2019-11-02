package com.lsolier.udacity.vehiclesapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientMapsConfig {

  @Value("${external-services.maps.endpoint}")
  private String endpoint;

  /**
   * Web Client for the maps (location) API
   * @return created maps endpoint
   */
  @Bean(name="maps")
  public WebClient webClientMaps() {
    return WebClient.create(endpoint);
  }
}
