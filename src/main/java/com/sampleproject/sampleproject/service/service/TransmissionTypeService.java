package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.responsedto.TransmissionTypeResponseDto;

import java.util.List;

public interface TransmissionTypeService {
    List<TransmissionTypeResponseDto> getAllTransmissionTypes();
}
