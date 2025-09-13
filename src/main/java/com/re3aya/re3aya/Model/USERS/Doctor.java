package com.re3aya.re3aya.Model.USERS;

import com.re3aya.re3aya.Model.Department;
import com.re3aya.re3aya.Model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;


    @ManyToOne
    private Department department;


    private String experience;
    private String specialization;
    private String officeNumber;
    private String workingHours;
    private Double price;
    private Double rating;
    private int numberOfExperienceYears;




}

