package com.sampleproject.sampleproject.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeResponseDto {
    private Integer id;

    private String vehicleType;

    private Integer ratePerKm;

}
