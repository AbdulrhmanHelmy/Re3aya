package com.re3aya.re3aya.Service.Doctor;

import com.re3aya.re3aya.DTO.DoctorDTO;
import com.re3aya.re3aya.Model.USERS.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor createDoctor(DoctorDTO doctor);
    Doctor updateDoctor(Long id, DoctorDTO doctor);
    void deleteDoctor(Long id);
    Optional<Doctor> getDoctorById(Long id);
    Page<Doctor> getAllDoctors(int size, int page);
    List<Doctor> getDoctorsByDepartment(Long departmentId);
    List<Doctor> getDoctorsBySpecialization(String specialization);
    List<Doctor> getDoctorsByExperienceYears(int experienceYears);
//    List<Doctor> getDoctorsByRating(double rating);

}
