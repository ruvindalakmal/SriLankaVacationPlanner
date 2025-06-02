package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.requestdto.VehicleRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.VehicleResponseDto;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface VehicleService {

    List<VehicleResponseDto> getAllVehicles();

    VehicleResponseDto deleteVehicle(Integer id) throws AccessDeniedException;

    String saveVehicle(VehicleRequestDto vehicleRequestDto);

    VehicleResponseDto updateVehicle(Integer id, VehicleRequestDto vehicleRequestDto);
}
