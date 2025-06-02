package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.dto.responsedto.DesignationResponseDto;
import com.sampleproject.sampleproject.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {

    Designation findByName(String driver);
}
