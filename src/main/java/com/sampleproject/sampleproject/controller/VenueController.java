package com.sampleproject.sampleproject.controller;

import com.sampleproject.sampleproject.dto.responsedto.VenueResponseDto;
import com.sampleproject.sampleproject.service.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RestController
public class VenueController {

    @Autowired
    public VenueService venueService;

    @GetMapping("/venue")
    public ModelAndView Venue() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView venueUI = new ModelAndView();
        venueUI.setViewName("Destination.html");
        venueUI.addObject("username", auth.getName());
        return venueUI;
    }

    @GetMapping("/venue/alldata")
    public List<VenueResponseDto> getAllVenues() {
        return venueService.getAllVenues();
    }
}
