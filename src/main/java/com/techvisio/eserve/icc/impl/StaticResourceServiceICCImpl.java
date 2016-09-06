package com.techvisio.eserve.icc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.service.UserService;

@Service
public class StaticResourceServiceICCImpl extends
		AbstractStaticResourceServiceICCImpl {

	@Autowired
	UserService userService;
	
	@Override
	public MultipartFile preSaveUserImage(MultipartFile imageFile, Long userId) {
		return super.preSaveUserImage(imageFile, userId);
	}

	@Override
	public String postSaveUserImage(String filePath, Long userId) {
		String imagePath= super.postSaveUserImage(filePath, userId);
		User user=userService.getUserWithUserPrivileges(userId);
		user.setPhotoPath(imagePath);
		userService.saveUser(user);
		return imagePath;
	}

	@Override
	public void preGetUserImage(Long userId) {
		super.preGetUserImage(userId);
	}

	@Override
	public FileSystemResource postGetUserImage(FileSystemResource imageResource) {
		// TODO Auto-generated method stub
		return super.postGetUserImage(imageResource);
	}

}
