package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.responsedto.TransmissionTypeResponseDto;
import com.sampleproject.sampleproject.service.service.TransmissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class TransmissionTypeController {

    @Autowired
    private TransmissionTypeService transmissionTypeService;

    @GetMapping(value = "/transmissionType/alldata")
    public List<TransmissionTypeResponseDto> getAllTransmissionTypes() {
        return transmissionTypeService.getAllTransmissionTypes();
    }
}
