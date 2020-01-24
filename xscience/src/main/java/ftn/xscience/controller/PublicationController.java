package ftn.xscience.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.service.PublicationService;
import ftn.xscience.service.ReviewService;
import ftn.xscience.utils.dom.DOMParser;

@RestController
@RequestMapping("/publication")
public class PublicationController {
	
	@Autowired
	PublicationService publicationService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	DOMParser domParser;

	
	
	@GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getPublicationById(@PathVariable("id") String id) {
		return null;
	}
	
	// ===================================== AUTOR =================================================================
	//@PreAuthorize("hasRole('EDITOR')")
	@PostMapping(value = "/rest/uploadPublication", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadPublication(@RequestParam("file") MultipartFile publication) throws IOException, SAXException, ParserConfigurationException, XMLDBException {
		
		String publStr = new String(publication.getBytes());
		System.out.println(publStr);
		publicationService.savePublication(publStr);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}/edit", 
			consumes = MediaType.TEXT_HTML_VALUE, 
			produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> editPublication(@PathVariable("id") String id) {
		return null;
	}
	
	// ili PUT ?
	// ovo ce biti logicko brisanje
	@PostMapping(value = "/{id}/withdraw")
	public ResponseEntity<?> withdrawPublication(@PathVariable("id") String id) {
		return null;
	}
	
	
	// ======================================== EDITOR ===============================================================
	
	// id = naziv u bazi
	@PostMapping(value = "/{id}/accept")
	public ResponseEntity<?> acceptPublication(@PathVariable("id") String documentId) throws XMLDBException {
		publicationService.acceptPublication(documentId);
		
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	// ovo moze biti i u sklopu assign-review
	// dodeli se review-er i promeni se status u in process ili sta vec
	@PostMapping(value = "/{id}/for-review")
	public ResponseEntity<?> sendPublicationForReview(@PathVariable("id") String id) {
		return null;
	}
	
	// post ?
	@PostMapping(value = "/{id}/reject")
	public ResponseEntity<?> rejectPublication(@PathVariable("id") String documentId) throws XMLDBException {
		publicationService.rejectPublication(documentId);
		return null;
	}
	
	// dodeljivanje recenzenta publikaciji
	@PostMapping(value = "/{id}/assign-reviewer/{reviewerId}")
	public ResponseEntity<?> assignReviewer(@PathVariable("id") String publicationId, @PathVariable("reviewerId") String reviewerId) {
		try {
			publicationService.assignReviewer(publicationId, reviewerId);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	// ======================================== RECENZENT =================================================================

	
}
