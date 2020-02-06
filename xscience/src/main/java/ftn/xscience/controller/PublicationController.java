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

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import ftn.xscience.dto.DTOConverter;
import ftn.xscience.dto.PublicationDTO;
import ftn.xscience.dto.UserDTO;
import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.user.TUser;
import ftn.xscience.security.JwtValidator;
import ftn.xscience.service.EMailService;
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
	
	@Autowired
	EMailService emailService;
	
	
	@GetMapping()
	public ResponseEntity<?> getAcceptedPublications() {
		return null;
	}
	
	@GetMapping(value = "/my-documents")
	public ResponseEntity<?> getMyDocuments(@RequestHeader("Authorization") final String token) {
		List<Publication> myDocuments = null;
		TUser loggedUser = jwtValidator.validate(token.substring(7));
				
	    myDocuments = publicationService.getMyDocuments(loggedUser);
		
		ArrayList<PublicationDTO> publicationsDTO = DTOConverter.convertPublicationsToDTO(myDocuments);
		
		return new ResponseEntity<ArrayList<PublicationDTO>>(publicationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/documents-for-review")
	public ResponseEntity<?> getDocumentsForReview(@RequestHeader("Authorization") final String token) {
		List<Publication> forReview = null;
		TUser loggedUser = jwtValidator.validate(token.substring(7));
		
		TUser reviewer = userService.getUserByEmail(loggedUser.getUsername());
		
		forReview = publicationService.getDocumentsForReview(reviewer);
		
		List<PublicationDTO> anonymized = DTOConverter.convertPublicationsToDTO(forReview);
		anonymized = publicationService.anonymize(anonymized);
		
		return new ResponseEntity<List<PublicationDTO>>(anonymized, HttpStatus.OK);
	}
	
	@GetMapping(value = "/documents-for-approval")
	public ResponseEntity<?> getDocumentsForApproval(@RequestHeader("Authorization") final String token) {
		List<Publication> forApproval = new ArrayList<Publication>();
		//TUser loggedUser = jwtValidator.validate(token);
		
		forApproval = publicationService.getDocumentsForApproval();
		
		List<PublicationDTO> forApprovalDTOs = DTOConverter.convertPublicationsToDTO(forApproval);
		
		return new ResponseEntity<List<PublicationDTO>>(forApprovalDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getPublicationById(@PathVariable("id") String publicationName) {
		
		String xsltString = publicationService.getTransformedPublication(publicationName);
		
		return new ResponseEntity<String>(xsltString, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAll/{status}")
	public ResponseEntity<?> getPublicationByStatus(@PathVariable("status") String status) throws JAXBException, XMLDBException {
		List<Publication> publications = publicationService.getPublicationsByStatus(status);
		
		System.out.println("===============\ndokumenti u koji su uploaded: \n");
		System.out.println(publications.size());
		
		ArrayList<PublicationDTO> publicationsDTO = DTOConverter.convertPublicationsToDTO(publications);
		
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
		
		ArrayList<PublicationDTO> publicationsDTO = DTOConverter.convertPublicationsToDTO(found);
		
		return new ResponseEntity<ArrayList<PublicationDTO>>(publicationsDTO, HttpStatus.OK);
	}
	
	// ===================================== AUTOR =================================================================
	

	// ===================================== AUTOR =================================================================
	//@PreAuthorize("hasRole('EDITOR')")
	@PostMapping(value = "/uploadPublication", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadPublication(@RequestParam("file") MultipartFile publicationFile,
											@RequestParam("revision") String revisionFlag) throws IOException, XMLDBException, JAXBException {

		publicationService.savePublication(publicationFile, Boolean.valueOf(revisionFlag));
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
	@PostMapping(value = "/{id}/export/{path}")
	public ResponseEntity<?> exportPublication(@PathVariable("id") String documentId, @PathVariable("path") String path){
		System.out.println("Document " + documentId + "will be exported to " + path );
		publicationService.exportPublication(documentId, path);
		return null;
	}
	
	// id = naziv u bazi
	@PostMapping(value = "/{id}/accept")
	public ResponseEntity<?> acceptPublication(@PathVariable("id") String documentId, 
												@RequestHeader("Authorization") final String token) throws XMLDBException, IOException, JAXBException, SAXException, ParserConfigurationException {
		TUser u = jwtValidator.validate(token.substring(7));
		TUser sender = userService.getUserByEmail(u.getUsername());
		publicationService.acceptPublication(documentId, sender);

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
	public ResponseEntity<?> rejectPublication(@PathVariable("id") String documentId,
												@RequestHeader("Authorization") final String token) throws XMLDBException, JAXBException, SAXException, ParserConfigurationException, IOException {
		System.out.println("Usao sam u reject");
		TUser u = jwtValidator.validate(token.substring(7));
		TUser sender = userService.getUserByEmail(u.getUsername());
		publicationService.rejectPublication(documentId, sender);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	// dodeljivanje recenzenta publikaciji
	//@PreAuthorize("hasRole('ROLE_EDITOR')")
	@PostMapping(value = "/{id}/assign-reviewer/{reviewerId}")
	public ResponseEntity<?> assignReviewer(@PathVariable("id") String publicationId, 
											@PathVariable("reviewerId") String reviewerId,
											@RequestHeader("Authorization") final String token) {
		System.out.println("Usao sam u assing reviewer");
		System.out.println(reviewerId);
		reviewerId = reviewerId + ".com";
		TUser u = jwtValidator.validate(token.substring(7));
		TUser loggedUser = userService.getUserByEmail(u.getUsername());
		try {
			publicationService.assignReviewer(publicationId, reviewerId, loggedUser);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	// ======================================== RECEZENT =================================================================

	
}
