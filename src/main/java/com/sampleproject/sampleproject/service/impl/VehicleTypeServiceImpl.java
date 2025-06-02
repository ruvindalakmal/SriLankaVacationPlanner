package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.dto.responsedto.VehicleTypeResponseDto;
import com.sampleproject.sampleproject.entity.VehicleType;
import com.sampleproject.sampleproject.repo.VehicleTypeRepository;
import com.sampleproject.sampleproject.service.service.VehicleTypeService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<VehicleTypeResponseDto> getAllVehicleTypes() {
     List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
        if(!vehicleTypes.isEmpty()){
            List<VehicleTypeResponseDto> vehicleTypeResponseDtos = objectMapper.vehicleTypeToVehicleTypeResponseDto(vehicleTypes);
            return vehicleTypeResponseDtos;
        }
        else {
            throw new RuntimeException("No vehicle type found");
        }
    }
}
