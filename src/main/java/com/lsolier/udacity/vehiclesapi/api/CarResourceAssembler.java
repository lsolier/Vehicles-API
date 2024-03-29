package com.lsolier.udacity.vehiclesapi.api;

import com.lsolier.udacity.vehiclesapi.domain.model.car.Car;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Maps the CarController to the Car class using HATEOAS
 */
@Component
public class CarResourceAssembler implements ResourceAssembler<Car, Resource<Car>> {

  @Override
  public Resource<Car> toResource(Car car) {
    return new Resource<>(car,
        linkTo(methodOn(CarController.class).get(car.getId())).withSelfRel(),
        linkTo(methodOn(CarController.class).list()).withRel("cars"));
  }

}
