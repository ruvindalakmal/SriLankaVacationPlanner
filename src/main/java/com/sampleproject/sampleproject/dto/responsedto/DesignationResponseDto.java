package com.sampleproject.sampleproject.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DesignationResponseDto {

    private Integer id;

    private String name;

    private Integer role_id;

    private Boolean need_user_account;
}
