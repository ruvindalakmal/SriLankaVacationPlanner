package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.requestdto.VehicleRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.VehicleResponseDto;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;
import com.sampleproject.sampleproject.service.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping("/transport")
    public ModelAndView transport(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView transportUI = new ModelAndView();
        transportUI.setViewName("Transport.html");
        transportUI.addObject("username", auth.getName());
        return transportUI;
    }

    @GetMapping("/transport/alldata")
    public List<VehicleResponseDto> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PostMapping("/transport/insert")
    public String saveVehicle(@RequestBody VehicleRequestDto vehicleRequestDto) throws AlreadyExistsException {
        return vehicleService.saveVehicle(vehicleRequestDto);
    }

    @DeleteMapping("/transport/delete/{id}")
    public VehicleResponseDto deleteVehicle(@PathVariable Integer id) throws AccessDeniedException {
        return vehicleService.deleteVehicle(id);
    }

    @PutMapping("/transport/update/{id}")
    public VehicleResponseDto updateVehicle(@PathVariable Integer id ,@RequestBody VehicleRequestDto vehicleRequestDto) throws AccessDeniedException, AlreadyExistsException {
        return vehicleService.updateVehicle(id, vehicleRequestDto);
    }
}
