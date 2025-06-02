package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.controller.UserPrivilegeController;
import com.sampleproject.sampleproject.dto.requestdto.VehicleRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.VehicleResponseDto;
import com.sampleproject.sampleproject.entity.Privilege;
import com.sampleproject.sampleproject.entity.User;
import com.sampleproject.sampleproject.entity.Vehicle;
import com.sampleproject.sampleproject.repo.UserRepository;
import com.sampleproject.sampleproject.repo.VehicleRepository;
import com.sampleproject.sampleproject.service.service.VehicleService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserPrivilegeController userPrivilegeController;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<VehicleResponseDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        if(!vehicles.isEmpty()){
            List<VehicleResponseDto> vehicleResponseDtos = objectMapper.vehicleToVehicleResponseDto(vehicles);
            return vehicleResponseDtos;
        }
        else {;
            throw new RuntimeException("No vehicle found");
        }
    }

    @Override
    public VehicleResponseDto deleteVehicle(Integer id) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "Vehicle");

        if (!userPrivilege.getPrivi_delete()){
            throw new AccessDeniedException("You do not have permission to delete this vehicle");
        }
        User loggedUser = userRepository.getByUsername(auth.getName());

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setDeleted_datetime(LocalDateTime.now());
        vehicle.setDeleted_userid(loggedUser.getId());
        vehicleRepository.save(vehicle);
        return objectMapper.vehicleToVehicleResponseDto(vehicle);
    }

    @Override
    public String saveVehicle(VehicleRequestDto vehicleRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "Vehicle");

        if (!userPrivilege.getPrivi_insert()){
            throw new AccessDeniedException("You do not have permission to add this vehicle");
        }
        User loggedUser = userRepository.getByUsername(auth.getName());

        vehicleRequestDto.setAdded_datetime(LocalDateTime.now());
        vehicleRequestDto.setAdded_userid(loggedUser.getId());

        Vehicle vehicle = objectMapper.vehicleRequestDtoToVehicle(vehicleRequestDto);
        vehicleRepository.save(vehicle);

        return "OK";
    }

    @Override
    public VehicleResponseDto updateVehicle(Integer id, VehicleRequestDto vehicleRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Privilege userPrivilege = userPrivilegeController.getPrivilegeByUserModule(auth.getName(), "Vehicle");

        if (!userPrivilege.getPrivi_update()){
            throw new AccessDeniedException("You do not have permission to update this vehicle");
        }
        User loggedUser = userRepository.getByUsername(auth.getName());
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));

        objectMapper.vehicleRequestDtoToVehicle(vehicleRequestDto , vehicle);
        vehicle.setModified_datetime(LocalDateTime.now());
        vehicle.setModified_userid(loggedUser.getId());
        vehicleRepository.save(vehicle);

        return objectMapper.vehicleToVehicleResponseDto(vehicle) ;
    }


}
