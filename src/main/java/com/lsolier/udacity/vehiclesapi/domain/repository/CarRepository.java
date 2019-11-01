package com.lsolier.udacity.vehiclesapi.domain.repository;

import com.lsolier.udacity.vehiclesapi.domain.model.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
