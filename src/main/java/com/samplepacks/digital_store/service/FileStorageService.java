//package com.samplepacks.digital_store.service;
//
//import jakarta.validation.Path;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//@Service
//public class FileStorageService {
//    private final Path fileStorageLocation;
//
//    @Autowired
//    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
//        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
//
//        try {
//            Files.createDirectories(this.fileStorageLocation);
//        } catch (Exception ex) {
//            throw new RuntimeException("Could not create upload directory", ex);
//        }
//    }
//
//    public String storeFile(MultipartFile file) {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        try {
//            // Validate file
//            if (fileName.contains("..")) {
//                throw new RuntimeException("Invalid file path");
//            }
//
//            // Copy file to target location
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return fileName;
//        } catch (IOException ex) {
//            throw new RuntimeException("Could not store file", ex);
//        }
//    }
//
//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new RuntimeException("File not found");
//            }
//        } catch (MalformedURLException ex) {
//            throw new RuntimeException("File not found", ex);
//        }
//    }
//}
