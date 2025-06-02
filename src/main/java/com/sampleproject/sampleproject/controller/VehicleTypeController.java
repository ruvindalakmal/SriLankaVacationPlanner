package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.responsedto.VehicleTypeResponseDto;
import com.sampleproject.sampleproject.service.service.VehicleTypeService;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @GetMapping(value = "/vehicleType/alldata")
    public List<VehicleTypeResponseDto> getAllVehicleTypes() {
        return vehicleTypeService.getAllVehicleTypes();
    }
}
