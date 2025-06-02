package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.AirConditioning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirConditioningRepository extends JpaRepository<AirConditioning, Integer> {
}
