package com.re3aya.re3aya.Controller.AdminControllers;

import com.re3aya.re3aya.Model.Department;
import com.re3aya.re3aya.Model.USERS.Assistant;
import com.re3aya.re3aya.Model.USERS.Doctor;
import com.re3aya.re3aya.Model.USERS.Patient;
import com.re3aya.re3aya.Model.User;
import com.re3aya.re3aya.Repository.DepartmentRepository;
import com.re3aya.re3aya.Repository.UserRepository;
import com.re3aya.re3aya.Repository.Users.AssistantRepository;
import com.re3aya.re3aya.Repository.Users.DoctorRepository;
import com.re3aya.re3aya.Repository.Users.PatientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkdb")
public class CheckDBController {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AssistantRepository assistantRepository;
    private final DepartmentRepository departmentRepository;

    public CheckDBController(UserRepository userRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, AssistantRepository assistantRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.assistantRepository = assistantRepository;
        this.departmentRepository = departmentRepository;
    }


    @GetMapping
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/assistants")
    public List<Assistant> getAllAssistants() {
        return assistantRepository.findAll();
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }



}
