package com.sampleproject.sampleproject.service.impl;

import com.sampleproject.sampleproject.dto.responsedto.VenueResponseDto;
import com.sampleproject.sampleproject.entity.Venue;
import com.sampleproject.sampleproject.repo.VenueRepository;
import com.sampleproject.sampleproject.service.service.VenueService;
import com.sampleproject.sampleproject.utils.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<VenueResponseDto> getAllVenues() {
        List<Venue> venues = venueRepository.findAll();
        if (!venues.isEmpty()) {
            List<VenueResponseDto> venueResponseDtos = objectMapper.venueToVenueResponseDto(venues);
            return venueResponseDtos;
        }else {
            throw new RuntimeException("No venue found");
        }
    }
}
