package ftn.xscience.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

	
	@GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getReviewById(@PathVariable("id") String id) {
		return null;
	}
	
	@PostMapping(consumes = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> uploadReview(@RequestBody String review) {
		return null;
	}
	
	@PutMapping(value = "/{id}/edit", consumes = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> editReview(@RequestBody String editedReview) {
		return null;
	}
	
	
}
