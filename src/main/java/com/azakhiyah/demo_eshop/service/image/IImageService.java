package com.azakhiyah.demo_eshop.service.image;

import org.springframework.web.multipart.MultipartFile;

import com.azakhiyah.demo_eshop.dto.ImageDto;
import com.azakhiyah.demo_eshop.model.Image;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,  Long imageId);
}
