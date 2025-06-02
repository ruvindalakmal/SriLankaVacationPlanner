package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.dto.responsedto.AirConditioningResponseDto;
import com.sampleproject.sampleproject.entity.AirConditioning;
import com.sampleproject.sampleproject.repo.AirConditioningRepository;
import com.sampleproject.sampleproject.service.service.AirConditioningService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirConditioningServiceImpl implements AirConditioningService {

    @Autowired
    private AirConditioningRepository airConditioningRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<AirConditioningResponseDto> getAllAirConditionings() {
        List<AirConditioning> airConditionings = airConditioningRepository.findAll();
        if (!airConditionings.isEmpty()){
            List<AirConditioningResponseDto> airConditioningResponseDtos = objectMapper.airConditioningToAirConditioningResponseDto(airConditionings);
            return airConditioningResponseDtos;
        }else {
            throw new RuntimeException("No air conditioning found");
        }
    }
}
