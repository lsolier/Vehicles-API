package com.lsolier.udacity.vehiclesapi.service;

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

  public CarService(CarRepository carRepository) {
    /**
     * TODO: Add the Maps and Pricing Web Clients you create
     *   in `VehiclesApiApplication` as arguments and set them here.
     */
    this.carRepository = carRepository;
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

    /**
     * TODO: Use the Pricing Web client you create in `VehiclesApiApplication`
     *   to get the price based on the `id` input'
     * TODO: Set the price of the car
     * Note: The car class file uses @transient, meaning you will need to call
     *   the pricing service each time to get the price.
     */


    /**
     * TODO: Use the Maps Web client you create in `VehiclesApiApplication`
     *   to get the address for the vehicle. You should access the location
     *   from the car object and feed it to the Maps service.
     * TODO: Set the location of the vehicle, including the address information
     * Note: The Location class file also uses @transient for the address,
     * meaning the Maps service needs to be called each time for the address.
     */


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