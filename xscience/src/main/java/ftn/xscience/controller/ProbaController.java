package ftn.xscience.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.repository.RepoProba;

@RestController
@RequestMapping("/get")
public class ProbaController {
	
	@Autowired
	RepoProba repo;
	
	@GetMapping(value="/doc")
	public ResponseEntity<String> getDoc() {
		try {
			repo.retrieveDocument();
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(value="/user")
	public ResponseEntity<String> getUser() {
		
		return null;
	}

}
