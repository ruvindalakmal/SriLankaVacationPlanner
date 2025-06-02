package com.sampleproject.sampleproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "emp_no",unique = true, nullable = false)
    //@Length(max = 6)
    private String empNo;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "calling_name" , nullable = false)
    private String callingName;

    @Column(unique = true,nullable = false)
    @Length(min = 10,max = 12)
    private String nic;

    @Column(nullable = false)
    private String Address;

    @Column(name = "mobile_number",unique = true,nullable = false)
    @Length(max = 10)
    private String mobileNumber;

    @Column(name = "land_number",unique = true)
    @Length(max = 10)
    private String landNumber;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate dob;

    private String note;

    @Column(name = "civil_status",nullable = false)
    private String civilStatus;

    @Column(nullable = false)
    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;

    @Column(nullable = false)
    private Integer added_userid;

    private Integer modified_userid;

    private Integer deleted_userid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "designation_id" , referencedColumnName = "id")
    private Designation designation_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeStatus_id" , referencedColumnName = "id")
    private EmployeeStatus employeeStatus_id;

//    @OneToOne(mappedBy = "employee")
//    private User user;




}
