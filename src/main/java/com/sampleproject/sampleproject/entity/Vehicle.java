package com.sampleproject.sampleproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vehicletype_id" , referencedColumnName = "id")
    private VehicleType vehicleType;

    @Column(name = "licensenumber", unique = true, nullable = false)
    private String licenseNo;

    @Column(name = "regdate", nullable = false)
    private LocalDate  registerDate;

    @Column(name = "manufacturer", unique = true, nullable = false)
    private String manufacturer;

    @Column(name = "model", unique = true, nullable = false)
    private String model;

    @Column(name = "year", unique = true, nullable = false)
    private Year year;

    @Column(name = "description")
    private String description;

    @Column(name = "seatcount" , unique = true, nullable = false)
    private Integer seatCount;

    @Column(nullable = false)
    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;

    @Column(nullable = false)
    private Integer added_userid;

    private Integer modified_userid;

    private Integer deleted_userid;

    @Column(name = "agencyname")
    private String agencyName;

    @Column(name = "agencycontactnumber")
    @Max(10)
    private String contactNumber;

    @Column(name = "chassisnumber" , unique = true, nullable = false)
    private String chassisNumber;

    @Column(name = "insuranceexpirydate" , unique = true, nullable = false)
    private LocalDate insuranceExpiryDate;

    @Column(name = "registrationexpirydate" , unique = true, nullable = false)
    private LocalDate registrationExpiryDate;

    private String color;

    @Column(name = "currentowner" , unique = true, nullable = false)
    private String ownerName;

    @Column(name = "currentowneraddress")
    private String ownerAddress;

    @Column(name = "currentownercontactnumber" , unique = true, nullable = false)
    private String ownerMobileNumber;

    @ManyToOne
    @JoinColumn(name = "transmissiontype_id" , referencedColumnName = "id")
    private TransmissionType transmissionType;

    @ManyToOne
    @JoinColumn(name = "fueltype_id" , referencedColumnName = "id")
    private FuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "airconditioning_id" , referencedColumnName = "id")
    private AirConditioning airConditioning;


}
