package com.lsolier.udacity.vehiclesapi.domain.model.manufacturer;

import com.lsolier.udacity.vehiclesapi.domain.model.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer extends Auditable<UUID> {

  @Id
  private Integer code;
  private String name;

}
