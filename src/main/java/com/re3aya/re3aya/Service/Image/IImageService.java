package com.re3aya.re3aya.Service.Image;

import com.re3aya.re3aya.Model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {

     Image upload(MultipartFile file)throws IOException ;
     void delete(Long id) throws IOException;

}
