package com.lsolier.udacity.vehiclesapi.domain.model.car;

import com.lsolier.udacity.vehiclesapi.domain.model.Auditable;
import com.lsolier.udacity.vehiclesapi.domain.model.Condition;
import com.lsolier.udacity.vehiclesapi.domain.model.Location;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Declares the Car class, related variables and methods.
 */
@Entity
@Getter
@Setter
public class Car extends Auditable<String> {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Condition condition;

  @Valid
  @Embedded
  private Details details = new Details();

  @Valid
  @Embedded
  private Location location = new Location(0d, 0d);

  @Transient
  private String price;

}
