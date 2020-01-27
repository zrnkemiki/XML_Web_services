package ftn.xscience.controller;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.repository.RepoProba;

@RestController
@RequestMapping("/proba")
public class ProbaController {
	
	@Autowired
	RepoProba repo;
	
	// MORA DA BUDE /REST ZA AUTORIZACIJU
	@PreAuthorize("hasRole('EDITOR')")
	@GetMapping(value="/doc")
	public ResponseEntity<String> getDoc(@RequestHeader("Authorization") String token) {
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
	
	@PostMapping(value="/proba")
	public ResponseEntity<?> createCol() {
		repo.createCol();
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@GetMapping(value="/extract")
	public ResponseEntity<String> extractMetadata() {
		System.out.println("BBBBBBBBBBBBBBBBB");
		try {
			repo.extractMetadata();
		} catch (SAXException | IOException | TransformerException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(value="/sparql")
	public ResponseEntity<String> sparqlProba() {
			
			try {
				repo.sparqlProba();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}