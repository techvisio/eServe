package com.techvisio.eserve.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techvisio.eserve.icc.StaticResourceServiceICC;
import com.techvisio.eserve.service.StaticResourceService;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ServiceLocator;

@Service
public class StaticResourceServiceImpl implements StaticResourceService {

	@Autowired
	ServiceLocator servicelocator;
	
	@Override
	public String saveUserImage(MultipartFile imageFile, Long userId) {
		StaticResourceServiceICC resourceServiceICC=servicelocator.getService(StaticResourceServiceICC.class);
		resourceServiceICC.preSaveUserImage(imageFile, userId);
		FileOutputStream writer=null;
		try{
			File rootDirectory=new File(CommonUtil.getUserImagePath());
			if(!rootDirectory.exists()){
				rootDirectory.mkdirs();
			}
			File destinationFile=new File(rootDirectory, ""+userId+".jpg");
			writer=new FileOutputStream(destinationFile);
			writer.write(imageFile.getBytes());
			writer.flush();
			}
			catch(Exception e){
				throw new RuntimeException("Error while saving the file "+e.getMessage());
			}
		finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException("Error while saving the file "+e.getMessage());
				}
			}
		}
		String filePath="../resources/user/image/"+userId;
		filePath=resourceServiceICC.postSaveUserImage(filePath, userId);
		return filePath;
	}

	@Override
	public FileSystemResource getUserImage(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
