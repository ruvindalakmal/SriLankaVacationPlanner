package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.entity.Module;
import com.sampleproject.sampleproject.repo.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;

    @GetMapping(value = "/module/alldata" , produces = "application/json")
    public List<Module> getAllModules(){
        return moduleRepository.findAll();
    }
}
