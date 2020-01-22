package ftn.xscience.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.xscience.dto.UserCredentials;
import ftn.xscience.model.TUser;
import ftn.xscience.security.JwtGenerator;
import ftn.xscience.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	
	@Autowired
	JwtGenerator jwtGenerator;
	
	@Autowired
	UserService userService;

	@GetMapping(value = "/user/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getUserByEmail(@PathVariable("id") String email) {
		
		return null;
	}
	
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
		System.out.println(credentials.getEmail());
		try {
			TUser user = userService.login(credentials);
			
			String token = jwtGenerator.generate(user);
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setBearerAuth(token);
			return ResponseEntity.ok().headers(responseHeaders).body(user);
			
		} catch (Exception e) {
			// hvataj ako je return user == null
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
}
