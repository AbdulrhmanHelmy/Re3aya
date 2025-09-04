package com.re3aya.re3aya.Service.Image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.re3aya.re3aya.Model.Image;
import com.re3aya.re3aya.Exeption.ResourceNotFound;
import com.re3aya.re3aya.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
@Service
public class ImageService implements IImageService{
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    @Autowired
    public ImageService(Cloudinary cloudinary, ImageRepository imageRepository) {
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
    }
    @Override
    @Transactional
    public Image upload(MultipartFile file) throws IOException {
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        Image image = new Image();
        image.setUrl(uploadResult.get("url").toString());
        image.setPubID(uploadResult.get("public_id").toString());
        return imageRepository.save(image);
    }
    @Transactional
    public void delete(Long id) throws IOException {
        Image image=imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Image not found"));
        cloudinary.uploader().destroy(image.getPubID(), ObjectUtils.emptyMap());
        imageRepository.delete(image);
    }
}
