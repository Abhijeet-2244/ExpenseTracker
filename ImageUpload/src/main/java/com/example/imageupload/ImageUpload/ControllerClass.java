package com.example.imageupload.ImageUpload;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.data.util.StreamUtils;
import org.springframework.util.*;

@RestController

public class ControllerClass {
	
	@GetMapping("/imageApi")
	public void getImage(HttpServletResponse response) throws IOException {
		
		
			InputStream stream= new FileInputStream("Images/dockers.png");
			response.setContentType("image/jpeg"); 
			org.springframework.util.StreamUtils.copy(stream, response.getOutputStream());

	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public String getException(FileNotFoundException fi) {
		return "Exception occurred, could not locate the file" +fi;
	}
	
	

}
