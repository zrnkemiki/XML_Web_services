package ftn.xscience.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.dto.PublicationDTO;
import ftn.xscience.dto.UserDTO;
import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.user.TUser;
import ftn.xscience.security.JwtValidator;
import ftn.xscience.service.PublicationService;
import ftn.xscience.service.ReviewService;
import ftn.xscience.service.UserService;
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

	@Autowired
	UserService userService;
	
	@Autowired
	JwtValidator jwtValidator;
	
	
	@GetMapping()
	public ResponseEntity<?> getAcceptedPublications() {
		return null;
	}
	
	@GetMapping(value = "/my-documents")
	public ResponseEntity<?> getMyDocuments(@RequestHeader("Authorization") final String token) {
		
		TUser loggedUser = jwtValidator.validate(token);
		if (loggedUser == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		
		publicationService.getMyDocuments(loggedUser);
		
		return null;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getPublicationById(@PathVariable("id") String id) {
		System.out.println("HEHEHE");
		return null;
	}
	
	@GetMapping(value = "/getAll/{status}")
	public ResponseEntity<?> getPublicationByStatus(@PathVariable("status") String status) throws JAXBException, XMLDBException {
		List<Publication> publications = publicationService.getPublicationsByStatus(status);
		
		System.out.println("===============\ndokumenti u koji su uploaded: \n");
		System.out.println(publications.size());
		ArrayList<PublicationDTO> publicationsDTO = new ArrayList<>();
		
		if(publications.size()!=0) {
			System.out.println(publications.get(0).getTitle());
			for (Publication publication : publications) {
				PublicationDTO temp = new PublicationDTO(publication);
				publicationsDTO.add(temp);
			}
		}
		System.out.println("Ovo je dto " + publicationsDTO.get(0).getTitle());
		
		return new ResponseEntity<ArrayList<PublicationDTO>>(publicationsDTO, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value="/search")
	public ResponseEntity<?> searchForPublication(@RequestParam Map<String,String> params) throws JAXBException, XMLDBException {
		System.out.println(params);
		// prosledjuje se kakav status treba da bude zavisno od korisnika i sta mu treba
		String status = "ACCEPTED";
		List<Publication> found = publicationService.searchPublications(params, status);
		System.out.println("===============\ndokumenti u kojima se nalazi pojam: \n");
		System.out.println(found.size());
		//VRATI MI PUBLICATION<>
		ArrayList<PublicationDTO> publications = new ArrayList<>();
		
		if(found.size()!=0) {
			for (Publication publication : found) {
				PublicationDTO temp = new PublicationDTO(publication);
				publications.add(temp);
			}
		}
		
		return new ResponseEntity<ArrayList<PublicationDTO>>(publications, HttpStatus.OK);
	}
	
	// ===================================== AUTOR =================================================================
	

	// ===================================== AUTOR =================================================================
	//@PreAuthorize("hasRole('EDITOR')")
	@PostMapping(value = "/rest/uploadPublication", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadPublication(@RequestParam("file") MultipartFile publicationFile) throws IOException, SAXException, ParserConfigurationException, XMLDBException {
		
		//String publStr = new String(publication.getBytes());
		//System.out.println(publStr);
		publicationService.savePublication(publicationFile);
		
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
	
	@GetMapping(value="/{id}/reviewer-suggestion")
	public ResponseEntity<?> reviewerSuggestion(@PathVariable("id") String documentId) throws XMLDBException, JAXBException {
		List<UserDTO> userFound = reviewService.reviewerSuggestionAlg(documentId);
		
		return new ResponseEntity<List<UserDTO>>(userFound, HttpStatus.OK);
	}
	
	
	// id = naziv u bazi
	@PostMapping(value = "/{id}/accept")
	public ResponseEntity<?> acceptPublication(@PathVariable("id") String documentId) throws XMLDBException {
		System.out.println("Usao sam ovde!");
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
	@PreAuthorize("hasRole('EDITOR')")
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
	
	
	// ======================================== RECEZENT =================================================================

	
}
