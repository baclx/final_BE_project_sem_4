package com.example.spring_jwt.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    public String uploadImage(MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                Path imagePath = Paths.get("src/main/resources/images/" + file.getOriginalFilename());
                Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return "Image uploaded successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image.";
        }
    }
}
