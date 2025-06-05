package com.sampleproject.sampleproject.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VenueResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String openingHours;

    private String district;
    private String province;
    private String venueStatus;

    private List<String> venueCategories;
    private List<String> venueActivities;

    private LocalDateTime added_datetime;

    private LocalDateTime lastModified_datetime;

    private LocalDateTime deleted_datetime;

    private Integer added_userid;

    private Integer lastmodified_userid;

    private Integer deleted_userid;
}
