package com.re3aya.re3aya.Model.USERS;

import com.re3aya.re3aya.Model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String experience;
    private String specialization;
    private String officeNumber;
    private String workingHours;
    private String profileImage;


//    @OneToMany(mappedBy = "doctor")
//    private List<Patient> patients;

//    @OneToMany(mappedBy = "doctor" ,fetch = FetchType.EAGER)
//    private List<Assistant> assistantList;




}

