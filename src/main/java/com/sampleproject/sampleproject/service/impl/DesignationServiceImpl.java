package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.dto.responsedto.DesignationResponseDto;
import com.sampleproject.sampleproject.entity.Designation;
import com.sampleproject.sampleproject.repo.DesignationRepository;
import com.sampleproject.sampleproject.service.service.DesignationService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public DesignationResponseDto getAllDriverDesignation() {
        Designation designation = designationRepository.findByName("Driver");
        if (designation != null){
            DesignationResponseDto designationResponseDtos =  objectMapper.designationToDesignationResponseDto(designation);
            return designationResponseDtos;
        }else {
            throw new RuntimeException("No designation found");
        }
    }
}
