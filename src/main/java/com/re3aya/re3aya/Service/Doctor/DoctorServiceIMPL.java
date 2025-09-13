package com.re3aya.re3aya.Service.Doctor;

import com.re3aya.re3aya.DTO.DoctorDTO;
import com.re3aya.re3aya.Exeption.ResourceNotFound;
import com.re3aya.re3aya.Model.ENUMS.Role;
import com.re3aya.re3aya.Model.USERS.Doctor;
import com.re3aya.re3aya.Model.User;
import com.re3aya.re3aya.Repository.DepartmentRepository;
import com.re3aya.re3aya.Repository.Users.DoctorRepository;
import com.re3aya.re3aya.Service.Department.DepartmentService;
import com.re3aya.re3aya.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceIMPL implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DoctorServiceIMPL(DoctorRepository doctorRepository, UserService userService, DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    Doctor mapDto(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        User user = userService.getUserById(doctorDTO.getUserId());
        doctor.setUser(user);
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setOfficeNumber(doctorDTO.getOfficeNumber());
        doctor.setWorkingHours(doctorDTO.getWorkingHours());
        doctor.setPrice(doctorDTO.getPrice());
        doctor.setNumberOfExperienceYears(doctorDTO.getNumberOfExperienceYears());
        doctor.setDepartment(departmentService.getDepartmentByName(doctorDTO.getDepartment()));
        return doctor;
    }

    @Override
    public Doctor createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = mapDto(doctorDTO);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        checkDoctor(doctor);
        doctor.setDepartment(departmentService.getDepartmentByName(doctorDTO.getDepartment()));
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setSpecialization(doctorDTO.getSpecialization().toUpperCase());
        doctor.setOfficeNumber(doctorDTO.getOfficeNumber());
        doctor.setWorkingHours(doctorDTO.getWorkingHours());
        doctor.setPrice(doctorDTO.getPrice());
        doctor.setNumberOfExperienceYears(doctorDTO.getNumberOfExperienceYears());
        return doctorRepository.save(doctor);
    }

    private void checkDoctor(Doctor doctor) {
        User user = userService.getCurrentUser();
        if (!user.getRole().equals(Role.ADMIN) || user.getPhoneNumber().equals(doctor.getUser().getPhoneNumber())) {
            throw new RuntimeException("You are not authorized to update this doctor");
        }
    }

    @Override
    public void deleteDoctor(Long id) {
        checkDoctor(doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Doctor not found")
        ));
        doctorRepository.deleteById(id);
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Page<Doctor> getAllDoctors(int size, int page) {
        return doctorRepository.findAll(
                PageRequest.of(page, size)
        );
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        return doctorRepository.findAllByDepartment(departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFound("Department not found")));
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findAllBySpecialization(specialization.toUpperCase());
    }

    @Override
    public List<Doctor> getDoctorsByExperienceYears(int experienceYears) {
        return doctorRepository.findAllByNumberOfExperienceYears(experienceYears);
    }

}
