package com.sampleproject.sampleproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "designation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Increment
    private Integer id;

    private String name;

    @Column(nullable = false)
    private Integer role_id;

    private Boolean need_user_account;

}
