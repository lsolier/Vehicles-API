package com.lsolier.udacity.vehiclesapi.service;

import com.lsolier.udacity.vehiclesapi.client.maps.MapsClient;
import com.lsolier.udacity.vehiclesapi.client.prices.PriceClient;
import com.lsolier.udacity.vehiclesapi.domain.model.Location;
import com.lsolier.udacity.vehiclesapi.domain.model.car.Car;
import com.lsolier.udacity.vehiclesapi.domain.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

  private final CarRepository carRepository;
  private final MapsClient mapsClient;
  private final PriceClient priceClient;

  public CarService(CarRepository carRepository,
                    MapsClient mapsClient,
                    PriceClient priceClient) {
    this.carRepository = carRepository;
    this.mapsClient = mapsClient;
    this.priceClient = priceClient;
  }

  /**
   * Gathers a list of all vehicles
   * @return a list of all vehicles in the CarRepository
   */
  public List<Car> list() {
    return carRepository.findAll();
  }

  /**
   * Gets car information by ID (or throws exception if non-existent)
   * @param id the ID number of the car to gather information on
   * @return the requested car's information, including location and price
   */
  public Car findById(Long id) {
    Optional<Car> carOptional = carRepository.findById(id);
    Car car = carOptional.orElseThrow(CarNotFoundException::new);

    String price = priceClient.getPrice(car.getId());
    car.setPrice(price);

    Location location = mapsClient.getAddress(car.getLocation());
    car.setLocation(location);

    return car;
  }

  /**
   * Either creates or updates a vehicle, based on prior existence of car
   * @param car A car object, which can be either new or existing
   * @return the new/updated car is stored in the repository
   */
  public Car save(Car car) {
    if (car.getId() != null) {
      return carRepository.findById(car.getId())
          .map(carToBeUpdated -> {
            carToBeUpdated.setDetails(car.getDetails());
            carToBeUpdated.setLocation(car.getLocation());
            return carRepository.save(carToBeUpdated);
          }).orElseThrow(CarNotFoundException::new);
    }
    return carRepository.save(car);
  }

  /**
   * Deletes a given car by ID
   * @param id the ID number of the car to delete
   */
  public void delete(Long id) {
    Car carToBeDeleted = carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    carRepository.delete(carToBeDeleted);
  }

}
