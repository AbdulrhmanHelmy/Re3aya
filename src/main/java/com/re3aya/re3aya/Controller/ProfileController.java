package com.re3aya.re3aya.Controller;

import com.re3aya.re3aya.DTO.ImageDto;
import com.re3aya.re3aya.DTO.UserDTO;
import com.re3aya.re3aya.Model.USERS.Assistant;
import com.re3aya.re3aya.Model.USERS.Doctor;
import com.re3aya.re3aya.Model.USERS.Patient;
import com.re3aya.re3aya.Model.User;
import com.re3aya.re3aya.Repository.Users.AssistantRepository;
import com.re3aya.re3aya.Repository.Users.DoctorRepository;
import com.re3aya.re3aya.Repository.Users.PatientRepository;
import com.re3aya.re3aya.Response.API_Response;
import com.re3aya.re3aya.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final DoctorRepository doctorRepository;
    private final AssistantRepository assistantRepository;
    private final PatientRepository patientRepository;

    public ProfileController(UserService userService, DoctorRepository doctorRepository, AssistantRepository assistantRepository, PatientRepository patientRepository) {
        this.userService = userService;
        this.doctorRepository = doctorRepository;
        this.assistantRepository = assistantRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public ResponseEntity<API_Response> getInfo() {
        User user = userService.getCurrentUser();
        return switch (user.getRole().toString()) {
            case "DOCTOR" -> {
                Doctor doctor = doctorRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Doctor profile not found"));
                yield ResponseEntity.ok(new API_Response("Doctor Founded", doctor));
            }
            case "ASSISTANT" -> {
                Assistant assistant = assistantRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Assistant profile not found"));
                yield ResponseEntity.ok(new API_Response("Assistant Found", assistant));
            }
            case "PATIENT" -> {
                Patient patient = patientRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Patient profile not found"));
                yield ResponseEntity.ok(new API_Response("Patient Found", patient));
            }
            case "ADMIN" -> ResponseEntity.ok(new API_Response("Admin Found",user));
            default -> throw new RuntimeException("Unknown role: " + user.getRole());
        };
    }


    @PostMapping("/img")
    public ResponseEntity<API_Response> addProductIMG( @RequestParam("image") MultipartFile file) throws IOException {
        API_Response apiResponse = new API_Response();
        ImageDto imageDto = new ImageDto(file);
        apiResponse.setData(userService.addProfileIMG(imageDto));
        apiResponse.setMessage(" Profile image added successfully");
        return ResponseEntity.ok(apiResponse);
    }



    @DeleteMapping("/img")
    public ResponseEntity<API_Response> deleteImage() throws IOException {
        API_Response apiResponse = new API_Response();
        apiResponse.setData(null);
        apiResponse.setMessage(" Image Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }

}
