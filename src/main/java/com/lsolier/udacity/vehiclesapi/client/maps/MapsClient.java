package com.lsolier.udacity.vehiclesapi.client.maps;

import com.lsolier.udacity.vehiclesapi.domain.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@Component
public class MapsClient {

  private final WebClient client;
  private final ModelMapper mapper;

  public MapsClient(WebClient client,
                    ModelMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  /**
   * Gets an address from the Maps client, given latitude and longitude.
   * @param location An object containing "lat" and "lon" of location
   * @return An updated location including street, city, state and zip,
   *   or an exception message noting the Maps service is down
   */
  public Location getAddress(Location location) {
    try {
      Address address = client
          .get()
          .uri(uriBuilder -> uriBuilder
              .path("/maps/")
              .queryParam("lat", location.getLat())
              .queryParam("lon", location.getLon())
              .build())
          .retrieve()
          .bodyToMono(Address.class)
          .block();
      mapper.map(Objects.requireNonNull(address), location);
      return location;
    } catch (Exception ex) {
      log.warn("Map service is down");
      return location;
    }
  }

}
