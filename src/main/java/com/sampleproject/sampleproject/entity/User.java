package com.sampleproject.sampleproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true,nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean status;

    private String note;

    private byte[] userimage;

    @ManyToOne()
    @JoinColumn(name = "employee_id" , referencedColumnName = "id")
    private Employee employee;

    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;

    private Integer modified_userid;

    private Integer deleted_userid;

    @ManyToMany(cascade = {CascadeType.MERGE , CascadeType.PERSIST})
    @JoinTable(
            name = "user_has_role" ,
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
