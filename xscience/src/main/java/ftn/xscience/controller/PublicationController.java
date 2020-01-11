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
@RequestMapping("/publication")
public class PublicationController {

	@GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getPublicationById(@PathVariable("id") String id) {
		return null;
	}
	
	// ===================================== AUTOR =================================================================
	
	@PostMapping(consumes = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> uploadPublication(@RequestBody String publication) {
		return null;
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
	
	@PostMapping(value = "/{id}/accept")
	public ResponseEntity<?> acceptPublication(@PathVariable("id") String id) {
		return null;
	}
	
	@PostMapping(value = "/{id}/for-review")
	public ResponseEntity<?> sendPublicationForReview(@PathVariable("id") String id) {
		return null;
	}
	
	// post ?
	@PostMapping(value = "/{id}/reject")
	public ResponseEntity<?> rejectPublication(@PathVariable("id") String id) {
		return null;
	}
	
	// dodeljivanje recenzenta publikaciji
	@PostMapping(value = "/{id}/assign-reviewer")
	public ResponseEntity<?> assignReviewer(@PathVariable("id") String publicationId, @RequestBody String reviewerId) {
		return null;
	}
	
	
	// ======================================== RECENZENT =================================================================
	// POST ?
	@PostMapping(value = "/{id}/accept-review")
	public ResponseEntity<?> acceptReview() {
		return null;
	}
	
	@PostMapping(value = "/{id}/decline-review")
	public ResponseEntity<?> declineReview() {
		return null;
	}
	
}
