package com.re3aya.re3aya.DTO;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long UserId;
    private String department;
    private String experience;
    private String specialization;
    private String officeNumber;
    private String workingHours;
    private Double price;
    private int numberOfExperienceYears;

}
