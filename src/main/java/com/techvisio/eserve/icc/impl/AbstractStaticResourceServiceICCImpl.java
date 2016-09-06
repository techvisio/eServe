package com.techvisio.eserve.icc.impl;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import com.techvisio.eserve.icc.StaticResourceServiceICC;

public abstract class AbstractStaticResourceServiceICCImpl implements StaticResourceServiceICC {

	@Override
	public MultipartFile preSaveUserImage(MultipartFile imageFile, Long userId) {
		return imageFile;
	}

	@Override
	public String postSaveUserImage(String filePath, Long userId) {
		return filePath;
	}

	@Override
	public void preGetUserImage(Long userId) {

	}

	@Override
	public FileSystemResource postGetUserImage(FileSystemResource imageResource) {
		return imageResource;
	}

}
