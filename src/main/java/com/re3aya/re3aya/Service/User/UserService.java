package com.re3aya.re3aya.Service.User;

import com.re3aya.re3aya.DTO.ImageDto;
import com.re3aya.re3aya.DTO.LoginDTO;
import com.re3aya.re3aya.DTO.UserDTO;
import com.re3aya.re3aya.Exeption.ResourceNotFound;
import com.re3aya.re3aya.Model.Image;
import com.re3aya.re3aya.Model.Session;
import com.re3aya.re3aya.Model.USERS.Patient;
import com.re3aya.re3aya.Model.User;
import com.re3aya.re3aya.Repository.SessionRepository;
import com.re3aya.re3aya.Repository.UserRepository;
import com.re3aya.re3aya.Repository.Users.PatientRepository;
import com.re3aya.re3aya.Response.LoginResponse;
import com.re3aya.re3aya.Service.Image.ImageService;
import com.re3aya.re3aya.Util.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final SessionRepository sessionRepository;
    private final ImageService imageService;
    private final PatientRepository patientRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtil jwtUtil, SessionRepository sessionRepository, ImageService imageService, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.sessionRepository = sessionRepository;
        this.imageService = imageService;
        this.patientRepository = patientRepository;
    }

    @Override
    public User Register(UserDTO userDTO) {
        if (userRepository.findByPhoneNumber(userDTO.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("PhoneNumber already registered");
        }

        User user = new User();
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getUsername());
        userRepository.save(user);
        Patient patient = new Patient();
        patient.setUser(user);
        patientRepository.save(patient);
        return user;
    }
    @Override
    public LoginResponse Login(LoginDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getPhoneNumber(), userDTO.getPassword()));
        User user = userRepository.findByPhoneNumber(userDTO.getPhoneNumber()).orElseThrow(
                () -> new ResourceNotFound("User Not Found"));
//        if (!user.getIsVerified()) {
//            throw new RuntimeException("Account is not verified");
//        }

        String token = jwtUtil.generateToken(user);
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setStartTime(LocalDateTime.now());
        session.setEndTime(LocalDateTime.now().plusHours(24));
        sessionRepository.save(session);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUser(user);
        return loginResponse;
    }

    @Override
    public User addProfileIMG(ImageDto imageDto) throws IOException {
        Image img = imageService.upload(imageDto.getMultipartFile());
        User user = getCurrentUser();
        user.setProfileImage(img);
        userRepository.save(user);
        return userRepository.save(user);
    }

    @Override
    public void deleteProfileIMG() throws IOException {
        User user = getCurrentUser();
        user.setProfileImage(null);
        userRepository.save(user);
        imageService.delete(user.getProfileImage().getId());

    }



    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }

        throw new RuntimeException("Principal is not of type User: " + principal.getClass());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not found"));
    }

}