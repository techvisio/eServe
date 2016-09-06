package com.techvisio.eserve.controller;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.service.StaticResourceService;
import com.techvisio.eserve.util.CommonUtil;

@RestController
@RequestMapping("resources")
public class StaticResourceController {
	
	@Autowired
	StaticResourceService staticResourceService;
	
	@RequestMapping(value="/user/upload/{userId}",method = RequestMethod.POST)
	public ResponseEntity<Response> uploadPic(@RequestParam("file") MultipartFile file,@PathVariable Long userId) {  
		Response response=new Response();
		staticResourceService.saveUserImage(file, userId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/image/{userId}",method = RequestMethod.GET,produces=MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<FileSystemResource> getUserPhoto(@PathVariable Long userId) {  
		ResponseEntity<FileSystemResource> responseEntity=null;
		try{
			File rootDirectory=new File(CommonUtil.getUserImagePath());
			File destinationFile=new File(rootDirectory, ""+userId+".jpg");
			FileSystemResource resource = new FileSystemResource(destinationFile);
	        responseEntity = new ResponseEntity<>(resource, HttpStatus.OK);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
        return responseEntity;
	}
}
