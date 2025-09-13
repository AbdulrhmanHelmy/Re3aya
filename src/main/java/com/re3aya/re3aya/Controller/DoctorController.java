package com.re3aya.re3aya.Controller;

import com.re3aya.re3aya.DTO.DoctorDTO;
import com.re3aya.re3aya.Response.API_Response;
import com.re3aya.re3aya.Service.Doctor.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    // create,update ,delete himself, get all doctors, get doctor by id, get doctor by department, get doctor by specialization, get doctor by experience years
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<API_Response> createDoctor(@RequestBody DoctorDTO doctor) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success");
        apiResponse.setData(doctorService.createDoctor(doctor));
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOCTOR','ADMIN')")
    public ResponseEntity<API_Response> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctor) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success");
        apiResponse.setData(doctorService.updateDoctor(id, doctor));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOCTOR','ADMIN')")
    public ResponseEntity<API_Response> deleteDoctor(@PathVariable Long id) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success Deleted");
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<API_Response> getDoctorById(@PathVariable Long id) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success");
        apiResponse.setData(doctorService.getDoctorById(id));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/all/{size}/{page}")
    public ResponseEntity<API_Response> getAllDoctors(@PathVariable int size,@PathVariable int page) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success");
        apiResponse.setData(doctorService.getAllDoctors(size, page));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<API_Response> getDoctorsByDepartment(@PathVariable Long departmentId) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success");
        apiResponse.setData(doctorService.getDoctorsByDepartment(departmentId));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<API_Response> getDoctorsBySpecialization(@PathVariable String specialization) {
        API_Response apiResponse = new API_Response();
        apiResponse.setMessage("Success");
        apiResponse.setData(doctorService.getDoctorsBySpecialization(specialization));
        return ResponseEntity.ok(apiResponse);
    }

//    @GetMapping("/experience/{experience}")
//    public ResponseEntity<API_Response> getDoctorsByExperience(@PathVariable int experience) {
//        API_Response apiResponse = new API_Response();
//        apiResponse.setMessage("Success");
//        apiResponse.setData(doctorService.getDoctorsByExperience(experience));
//        return ResponseEntity.ok(apiResponse);
//    }



}
