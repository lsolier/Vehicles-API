package com.lsolier.udacity.vehiclesapi.client.maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Declares a class to store an address, city, state and zip code.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

  private String address;
  private String city;
  private String state;
  private String zip;

}
