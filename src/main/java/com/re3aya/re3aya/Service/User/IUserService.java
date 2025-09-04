package com.re3aya.re3aya.Service.User;

import com.re3aya.re3aya.DTO.ImageDto;
import com.re3aya.re3aya.DTO.UserDTO;
import com.re3aya.re3aya.Model.User;

import java.io.IOException;

public interface IUserService {

    public String Login(UserDTO userDTO);
    public UserDTO Register(UserDTO userDTO);


    User addProfileIMG(ImageDto imageDto) throws IOException;

    void deleteProfileIMG() throws IOException;
}
