package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServiceImpl implements ImageService {
//    @Value("${image.upload.directory}")
    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir, "images");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }
    }
}
