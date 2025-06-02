package com.sampleproject.sampleproject.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sampleproject.sampleproject.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Boolean status;

    private String note;

    private byte[] userimage;

    private EmployeeResponseDto employeeResponseDto;

    private Set<RoleResponseDto> roles;

    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;

    private Integer modified_userid;

    private Integer deleted_userid;
}
