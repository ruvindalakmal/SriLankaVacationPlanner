package com.sampleproject.sampleproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "opening_hours", nullable = false)
    private String openingHours;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "venuestatus_id", nullable = false)
    private VenueStatus venueStatus;

    @ManyToMany
    @JoinTable(
            name = "venue_has_venuecategory",
            joinColumns = @JoinColumn(name = "venue_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "venuecategory_id", referencedColumnName = "id")
    )
    private List<VenueCategory> venueCategories;

    @ManyToMany
    @JoinTable(
            name = "venue_has_venueactivities",
            joinColumns = @JoinColumn(name = "venue_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "venueactivities_id", referencedColumnName = "id")
    )
    private List<VenueActivities> venueActivities;

    @Column( nullable = false)
    private LocalDateTime added_datetime;

    private LocalDateTime lastModified_datetime;

    private LocalDateTime deleted_datetime;

    @Column(nullable = false)
    private Integer added_userid;

    private Integer lastmodified_userid;

    private Integer deleted_userid;

}
