package com.sampleproject.sampleproject.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DesignationRequestDto {

    private String name;

    private Integer role_id;

    private Boolean need_user_account;
}
