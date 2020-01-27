package ftn.xscience.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.service.ReviewService;

@RestController
@RequestMapping("/reviewer-mng")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;

	
	@GetMapping(value = "/review/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getReviewById(@PathVariable("id") String id) {
		return null;
	}
	
	@PostMapping(value = "/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadReview(@RequestParam("file") MultipartFile review) throws IOException, SAXException, ParserConfigurationException, XMLDBException {
		
		String reviewStr = new String(review.getBytes());
		System.out.println(reviewStr);
		reviewService.saveReview(reviewStr);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/review/{id}/edit", consumes = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> editReview(@RequestBody String editedReview) {
		return null;
	}
	
	
	@PostMapping(value = "/publication/{id}/decline-review")
	public ResponseEntity<?> declineReview(@RequestHeader("Authorization") final String token, @PathVariable("id") String documentId) throws JAXBException {
		reviewService.declineReview(token.substring(7), documentId);
		return null;
	}
	
	
}