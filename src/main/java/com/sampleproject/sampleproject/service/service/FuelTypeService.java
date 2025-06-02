package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.responsedto.FuelTypeResponseDto;

import java.util.List;

public interface FuelTypeService {
    List<FuelTypeResponseDto> getAllFuelTypes();
}
