package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.responsedto.AirConditioningResponseDto;

import java.util.List;

public interface AirConditioningService {
    List<AirConditioningResponseDto> getAllAirConditionings();
}
