package com.re3aya.re3aya.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.re3aya.re3aya.Model.USERS.Assistant;
import com.re3aya.re3aya.Model.USERS.Doctor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> doctors;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assistant> assistants;


}
