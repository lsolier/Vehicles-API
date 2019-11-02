package com.lsolier.udacity.vehiclesapi.config.init;

import com.lsolier.udacity.vehiclesapi.domain.model.manufacturer.Manufacturer;
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
  CommandLineRunner initDatabase(ManufacturerRepository repository) {
    return args -> {
      repository.save(new Manufacturer(100, "Audi"));
      repository.save(new Manufacturer(101, "Chevrolet"));
      repository.save(new Manufacturer(102, "Ford"));
      repository.save(new Manufacturer(103, "BMW"));
      repository.save(new Manufacturer(104, "Dodge"));
    };
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
