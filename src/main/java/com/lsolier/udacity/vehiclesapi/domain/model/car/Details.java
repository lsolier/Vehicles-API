package com.lsolier.udacity.vehiclesapi.domain.model.car;

import com.lsolier.udacity.vehiclesapi.domain.model.manufacturer.Manufacturer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Declares the additional detail variables for each Car object,
 * along with related methods for access and setting.
 */
@Embeddable
@Getter
@Setter
public class Details {

  @NotBlank
  private String body;

  @NotBlank
  private String model;

  @NotNull
  @ManyToOne
  private Manufacturer manufacturer;

  private Integer numberOfDoors;

  private String fuelType;

  private String engine;

  private Integer mileage;

  private Integer modelYear;

  private Integer productionYear;

  private String externalColor;
}
