package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.repo.EmployeeStatusRepository;
import com.sampleproject.sampleproject.entity.EmployeeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeStatusController {

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @GetMapping(value = "/employeeStatus/alldata")
    public List<EmployeeStatus> getAllEmployeeStatus() {
        return employeeStatusRepository.findAll();
    }

}
