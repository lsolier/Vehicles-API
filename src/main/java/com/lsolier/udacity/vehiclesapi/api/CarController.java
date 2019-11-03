package com.lsolier.udacity.vehiclesapi.api;

import com.lsolier.udacity.vehiclesapi.domain.model.car.Car;
import com.lsolier.udacity.vehiclesapi.service.CarService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
@ApiResponses(value = {
    @ApiResponse(code=400, message = "This is a bad request, please follow the API documentation for the proper request format."),
    @ApiResponse(code=401, message = "Due to security constraints, your access request cannot be authorized. "),
    @ApiResponse(code=500, message = "The server is down. Please make sure that the Location microservice is running.")
})
public class CarController {

  private final CarService carService;
  private final CarResourceAssembler carAssembler;


  public CarController(CarService carService,
                       CarResourceAssembler carAssembler) {
    this.carService = carService;
    this.carAssembler = carAssembler;
  }

  /**
   * Creates a list to store any vehicles.
   * @return list of vehicles
   */
  @GetMapping
  Resources<Resource<Car>> list() {
    List<Resource<Car>> resources = carService.list().stream().map(carAssembler::toResource)
        .collect(Collectors.toList());
    return new Resources<>(resources, linkTo(methodOn(CarController.class).list()).withSelfRel());
  }

  /**
   * Gets information of a specific car by ID.
   * @param id the id number of the given vehicle
   * @return all information for the requested vehicle
   */
  @GetMapping("/{id}")
  Resource<Car> get(@PathVariable Long id) {
    Car car = carService.findById(id);
    return carAssembler.toResource(car);
  }

  /**
   * Posts information to create a new vehicle in the system.
   * @param car A new vehicle to add to the system.
   * @return response that the new vehicle was added to the system
   * @throws URISyntaxException if the request contains invalid fields or syntax
   */
  @PostMapping
  ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
    Resource<Car> resource = carAssembler.toResource(carService.save(car));
    return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
  }

  /**
   * Updates the information of a vehicle in the system.
   * @param id The ID number for which to update vehicle information.
   * @param car The updated information about the related vehicle.
   * @return response that the vehicle was updated in the system
   */
  @PutMapping("/{id}")
  ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
    car.setId(id);
    Resource<Car> resource = carAssembler.toResource(carService.save(car));
    return ResponseEntity.ok(resource);
  }

  /**
   * Removes a vehicle from the system.
   * @param id The ID number of the vehicle to remove.
   * @return response that the related vehicle is no longer in the system
   */
  @DeleteMapping("/{id}")
  ResponseEntity<?> delete(@PathVariable Long id) {
    carService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
