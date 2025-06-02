package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Integer> {
}
