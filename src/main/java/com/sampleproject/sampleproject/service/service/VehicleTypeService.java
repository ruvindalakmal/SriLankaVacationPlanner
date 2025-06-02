package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.responsedto.VehicleTypeResponseDto;

import java.util.List;

public interface VehicleTypeService {
    List<VehicleTypeResponseDto> getAllVehicleTypes();
}
