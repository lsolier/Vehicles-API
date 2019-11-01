package com.lsolier.udacity.vehiclesapi.domain.repository;

import com.lsolier.udacity.vehiclesapi.domain.model.manufacturer.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

}
