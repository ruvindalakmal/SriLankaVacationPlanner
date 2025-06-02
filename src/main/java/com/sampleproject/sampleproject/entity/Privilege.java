package com.sampleproject.sampleproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "privilege")
@ToString
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private Boolean privi_select;
    @NonNull
    private Boolean privi_insert;
    @NonNull
    private Boolean privi_update;
    @NonNull
    private Boolean privi_delete;

    @Column(nullable = false)
    private LocalDateTime added_datetime;

    private LocalDateTime modified_datetime;

    private LocalDateTime deleted_datetime;

    @Column(nullable = false)
    private Integer added_userid;

    private Integer modified_userid;

    private Integer deleted_userid;

    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private Module module_id;

    @ManyToOne
    @JoinColumn(name = "role_id" , referencedColumnName = "id")
    private Role role_id;


}
