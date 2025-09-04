package com.re3aya.re3aya.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    String phoneNumber;
    String password;
    private String username;


}