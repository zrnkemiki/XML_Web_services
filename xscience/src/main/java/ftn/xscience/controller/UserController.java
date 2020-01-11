package ftn.xscience.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.xscience.dto.UserCredentials;

@RestController
public class UserController {

	@GetMapping(value = "/user/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getUserByEmail(@PathVariable("id") String email) {
		
		return null;
	}
	
	
	@PostMapping()
	public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
		return null;
	}
	
}
