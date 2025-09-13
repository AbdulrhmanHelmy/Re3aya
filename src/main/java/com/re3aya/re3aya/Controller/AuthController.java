package com.re3aya.re3aya.Controller;


import com.re3aya.re3aya.DTO.LoginDTO;
import com.re3aya.re3aya.DTO.UserDTO;
import com.re3aya.re3aya.Response.API_Response;
import com.re3aya.re3aya.Response.LoginResponse;
import com.re3aya.re3aya.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<API_Response> login(@RequestBody LoginDTO logDTO) {
        LoginResponse loginResponse = userService.Login(logDTO);
        return ResponseEntity.ok(new API_Response("Success", loginResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<API_Response> register(@RequestBody UserDTO userDTO) {
        userService.Register(userDTO);

        return ResponseEntity.ok(new API_Response("success", userDTO));
    }
}


