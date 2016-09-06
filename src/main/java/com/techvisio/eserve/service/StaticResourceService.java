package com.techvisio.eserve.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;


public interface StaticResourceService {
String saveUserImage(MultipartFile imageFile,Long userId);
FileSystemResource getUserImage(Long userId);
}
