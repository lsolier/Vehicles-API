package com.lsolier.udacity.vehiclesapi.client.prices;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class PriceClient {

  private final WebClient client;
  private final ModelMapper mapper;

  public PriceClient(WebClient client,
                     ModelMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  // In a real-world application we'll want to add some resilience
  // to this method with retries/CB/failover capabilities
  // We may also want to cache the results so we don't need to
  // do a request every time

  /**
   * Gets a vehicle price from the pricing client, given vehicle ID.
   *
   * @param vehicleId ID number of the vehicle for which to get the price
   * @return Currency and price of the requested vehicle,
   * error message that the vehicle ID is invalid, or note that the
   * service is down.
   */
  public String getPrice(Long vehicleId) {
    try {
      Price price = client
          .get()
          .uri(uriBuilder -> uriBuilder
              .path("services/price/")
              .queryParam("vehicleId", vehicleId)
              .build())
          .retrieve().bodyToMono(Price.class).block();
      return String.format("%s %s", price.getCurrency(), price.getPrice());
    } catch (Exception ex) {
      log.error("Unexpected error retrieving price for vehicle {}", vehicleId, ex);
      return "(consult price)";
    }
  }

}