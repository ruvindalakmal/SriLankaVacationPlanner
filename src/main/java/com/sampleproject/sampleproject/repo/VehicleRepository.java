package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

}
