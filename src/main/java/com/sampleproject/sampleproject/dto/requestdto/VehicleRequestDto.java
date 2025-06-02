package com.sampleproject.sampleproject.dto.requestdto;

import com.sampleproject.sampleproject.entity.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequestDto {

    private VehicleType vehicleType;

    private String licenseNo;

    private LocalDate registerDate;

    private String manufacturer;

    private String model;

    private Year year;

    private String description;

    private Integer seatCount;

    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;

    private Integer added_userid;

    private Integer modified_userid;

    private Integer deleted_userid;

    private String agencyName;

    private String contactNumber;

    private String chassisNumber;

    private LocalDate insuranceExpiryDate;

    private LocalDate registrationExpiryDate;

    private String color;

    private String ownerName;

    private String ownerAddress;

    private String ownerMobileNumber;

    private TransmissionType transmissionType;

    private FuelType fuelType;

    private AirConditioning airConditioning;
}
