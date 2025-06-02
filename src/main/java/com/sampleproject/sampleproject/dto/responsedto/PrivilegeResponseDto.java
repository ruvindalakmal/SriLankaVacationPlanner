package com.sampleproject.sampleproject.dto.responsedto;

import com.sampleproject.sampleproject.entity.Module;
import com.sampleproject.sampleproject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeResponseDto {
    private Integer id;

    private Boolean privi_select;

    private Boolean privi_insert;

    private Boolean privi_update;

    private Boolean privi_delete;

    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;

    private Integer added_userid;

    private Integer modified_userid;

    private Integer deleted_userid;

    private Module module_id;

    private Role role_id;

    private String message;
}
