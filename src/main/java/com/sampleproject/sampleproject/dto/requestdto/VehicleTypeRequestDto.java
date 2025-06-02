package com.sampleproject.sampleproject.dto.requestdto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeRequestDto {

    private String vehicleType;

    private Integer ratePerKm;
}
