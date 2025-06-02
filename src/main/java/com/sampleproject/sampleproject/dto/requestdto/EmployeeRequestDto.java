package com.sampleproject.sampleproject.dto.requestdto;

import com.sampleproject.sampleproject.entity.Designation;
import com.sampleproject.sampleproject.entity.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {

    private Integer id;

    private String empNo;

    private String fullName;

    private String callingName;

    private String nic;

    private String Address;

    private String mobileNumber;

    private String landNumber;

    private String email;

    private String gender;

    private LocalDate dob;

    private String note;

    private String civilStatus;

    private Designation designation_id;

    private EmployeeStatus employeeStatus_id;

    private Integer added_userid;

    private Integer modified_userid;

    private Integer deleted_userid;

    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;
}
