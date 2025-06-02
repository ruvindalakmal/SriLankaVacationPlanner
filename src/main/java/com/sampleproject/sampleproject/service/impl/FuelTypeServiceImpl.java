package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.dto.responsedto.FuelTypeResponseDto;
import com.sampleproject.sampleproject.entity.FuelType;
import com.sampleproject.sampleproject.repo.FuelTypeRepository;
import com.sampleproject.sampleproject.service.service.FuelTypeService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTypeServiceImpl implements FuelTypeService {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<FuelTypeResponseDto> getAllFuelTypes() {
        List<FuelType> fuelTypes = fuelTypeRepository.findAll();

        if (!fuelTypes.isEmpty()){
            List<FuelTypeResponseDto> fuelTypeResponseDtos = objectMapper.fuelTypeToFuelTypeResponseDto(fuelTypes);
            return fuelTypeResponseDtos;
        }else {
            throw new RuntimeException("No fuel type found");
        }
    }
}
