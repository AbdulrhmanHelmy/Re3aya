package com.re3aya.re3aya.Response;

import com.re3aya.re3aya.Model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private User user;
}
