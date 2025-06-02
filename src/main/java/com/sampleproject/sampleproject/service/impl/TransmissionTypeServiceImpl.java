package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.dto.responsedto.TransmissionTypeResponseDto;
import com.sampleproject.sampleproject.entity.TransmissionType;
import com.sampleproject.sampleproject.repo.TransmissionTypeRepository;
import com.sampleproject.sampleproject.service.service.TransmissionTypeService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmissionTypeServiceImpl implements TransmissionTypeService {

    @Autowired
    private TransmissionTypeRepository transmissionTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<TransmissionTypeResponseDto> getAllTransmissionTypes() {
        List<TransmissionType> transmissionTypes = transmissionTypeRepository.findAll();
        if (!transmissionTypes.isEmpty()) {
            List<TransmissionTypeResponseDto> transmissionTypeResponseDtos = objectMapper.transmissionTypeToTransmissionTypeResponseDto(transmissionTypes);
            return transmissionTypeResponseDtos;
        } else {
            throw new RuntimeException("No transmission type found");
        }
    }
}
