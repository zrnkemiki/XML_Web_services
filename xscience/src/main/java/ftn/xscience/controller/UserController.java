package ftn.xscience.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import antlr.collections.List;
import ftn.xscience.dto.UserCredentials;
import ftn.xscience.dto.UserDTO;
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
			
			//HttpHeaders responseHeaders = new HttpHeaders();
			
			UserDTO userDto = new UserDTO(user);
			userDto.setJwtToken(token);
			
			//responseHeaders.setBearerAuth(token);
			//return ResponseEntity.ok().headers(responseHeaders).body(user);
			return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
			
		} catch (Exception e) {
			// hvataj ako je return user == null
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	//ZA TESTIRANJE AUTORIZACIJE!
	@GetMapping(value = "/testiranje", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVehicle() {
		ArrayList<String> titlovi = new ArrayList<String>();
		titlovi.add("prvi");
		titlovi.add("drugi");
		return new ResponseEntity<ArrayList<String>>(titlovi, HttpStatus.OK);
	}
}
