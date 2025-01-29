package com.fcc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcc.util.Constants;

@RequestMapping("/api")
@RestController
public class MediaController {
	
	@Value("${server.dir}")
	private String serverDir;

	@GetMapping("/picture/{filename}")
	public ResponseEntity<byte[]> getImage(@PathVariable String filename) 
			throws IOException {
		  File fi = new File(serverDir+"/"+filename);
		  byte[] fileContent = Files.readAllBytes(fi.toPath());

	      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
	}
}
