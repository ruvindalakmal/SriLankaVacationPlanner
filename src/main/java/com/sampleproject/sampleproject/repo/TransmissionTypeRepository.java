package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Integer> {
}
