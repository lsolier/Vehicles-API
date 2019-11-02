package com.lsolier.udacity.vehiclesapi.config.init;

import com.lsolier.udacity.vehiclesapi.domain.model.Condition;
import com.lsolier.udacity.vehiclesapi.domain.model.Location;
import com.lsolier.udacity.vehiclesapi.domain.model.car.Car;
import com.lsolier.udacity.vehiclesapi.domain.model.car.Details;
import com.lsolier.udacity.vehiclesapi.domain.model.manufacturer.Manufacturer;
import com.lsolier.udacity.vehiclesapi.domain.repository.CarRepository;
import com.lsolier.udacity.vehiclesapi.domain.repository.ManufacturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {

  /**
   * Initializes the car manufacturers available to the Vehicle API.
   * @param repository where the manufacturer information persists.
   * @return the car manufacturers to add to the related repository
   */
  @Bean
  CommandLineRunner initDatabase(CarRepository carRepository,
                                 ManufacturerRepository repository) {
    return args -> {
      String user = "load_user";
      Manufacturer manufacturer = new Manufacturer(100, "Porsche");
      manufacturer.setCreatedBy(user);
      manufacturer.setLastModifiedBy(user);
      repository.save(manufacturer);

      manufacturer = new Manufacturer(101, "Chevrolet");
      manufacturer.setCreatedBy(user);
      manufacturer.setLastModifiedBy(user);
      repository.save(manufacturer);

      manufacturer = new Manufacturer(102, "Ford");
      manufacturer.setCreatedBy(user);
      manufacturer.setLastModifiedBy(user);
      repository.save(manufacturer);

      manufacturer = new Manufacturer(103, "Dodge");
      manufacturer.setCreatedBy(user);
      manufacturer.setLastModifiedBy(user);
      repository.save(manufacturer);

      manufacturer = new Manufacturer(104, "BMW");
      manufacturer.setCreatedBy(user);
      manufacturer.setLastModifiedBy(user);
      repository.save(manufacturer);

      Car car = new Car();
      car.setCondition(Condition.USED);
      Details details = new Details();
      details.setBody("BMW 2 Series Gran Coupe M235i (306 Hp) xDrive Steptronic");
      details.setModel("2 Series");
      details.setManufacturer(manufacturer);
      details.setNumberOfDoors(4);
      details.setFuelType("Gasoline");
      details.setEngine("1998 cm3");
      details.setMileage(1500);
      details.setModelYear(2019);
      details.setProductionYear(2019);
      details.setExternalColor("Blue");
      car.setDetails(details);
      car.setLocation(new Location(-12.099400, -77.019961));
      car.setCreatedBy(user);
      car.setLastModifiedBy(user);
      carRepository.save(car);

    };
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
