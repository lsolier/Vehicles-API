package com.lsolier.udacity.vehiclesapi.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Location {

  @NotNull
  private Double lon;

  @NotNull
  private Double lat;

  @Transient
  private String address;

  @Transient
  private String city;

  @Transient
  private String state;

  @Transient
  private String zip;

  public Location(Double lon, Double lat) {
    this.lon = lon;
    this.lat = lat;
  }

}
