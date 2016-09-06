package com.techvisio.eserve.icc;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public interface StaticResourceServiceICC {
	MultipartFile preSaveUserImage(MultipartFile imageFile,Long userId);
	String postSaveUserImage(String filePath,Long userId);
	void preGetUserImage(Long userId);
	FileSystemResource postGetUserImage(FileSystemResource imageResource);
}
