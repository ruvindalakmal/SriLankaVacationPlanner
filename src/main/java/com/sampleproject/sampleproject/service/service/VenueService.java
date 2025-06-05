package com.sampleproject.sampleproject.service.service;

import com.sampleproject.sampleproject.dto.responsedto.VenueResponseDto;

import java.util.List;

public interface VenueService {
    List<VenueResponseDto> getAllVenues();
}
