package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.responsedto.DesignationResponseDto;
import com.sampleproject.sampleproject.repo.DesignationRepository;
import com.sampleproject.sampleproject.entity.Designation;
import com.sampleproject.sampleproject.service.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Component
@RestController
public class DesignationController {
    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationService designationService;

    @GetMapping(value = "/designation/alldata" , produces = "application/json")
    public List<Designation> getAllDesignations() {

       return designationRepository.findAll();
    }

}
