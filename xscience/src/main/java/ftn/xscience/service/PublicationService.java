package ftn.xscience.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.exception.DocumentNotFoundException;
import ftn.xscience.exception.UnmarshallingException;
import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.user.ObjectFactory;
import ftn.xscience.model.user.TUser;
import ftn.xscience.repository.PublicationRepository;
import ftn.xscience.repository.UserRepository;
import ftn.xscience.utils.dom.DOMParser;
import ftn.xscience.utils.dom.StringPathHandler;

@Service
public class PublicationService {
	
	@Autowired
	ServletContext context;

	@Autowired
	PublicationRepository publicationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DOMParser domParser;
	
	
	private static String schemaLocation = "WEB-INF/classes/data/xsd/publication.xsd";
	
	public String savePublication(MultipartFile publicationFile) throws SAXException, ParserConfigurationException, IOException, XMLDBException {
		String contextPath = context.getRealPath("/");
		
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		
		String publicationXml = new String(publicationFile.getBytes());
		Document publication = domParser.buildDocument(publicationXml, schemaPath);
		
		String publicationName = publication.getElementsByTagName("Title").item(0).getTextContent();
		
		
		// extract metadata FIRST
		
		
		publicationRepository.save(publicationXml, publicationName);
		return "";
	}
	
	public void acceptPublication(String documentId) throws XMLDBException {
		long mods = publicationRepository.updatePublicationStatus(documentId, "ACCEPTED");
		System.out.println("[INFO] " + mods + " made on document [" + documentId + "]");
	}
	
	public void withdrawPublication(String documentId) throws XMLDBException {
		long mods = publicationRepository.updatePublicationStatus(documentId, "WITHDRAWN");
		System.out.println("[INFO] " + mods + " made on document [" + documentId + "]");
	}
	
	public void rejectPublication(String documentId) throws XMLDBException {
		long mods = publicationRepository.updatePublicationStatus(documentId, "REJECTED");
		System.out.println("[INFO] " + mods + " made on document [" + documentId + "]");
	}
	
	// prepare for review
	// znaci uklanja se sve o autorima
	// moze se podeliti u 2 dokuemnta gde ce jedan biti samo naslov rada i autori/koautori
	// a drugi dokument samo content rada
	public String publicationAnonymization(String publicationXml) {
		
		return "";
	}
	
	public void assignReviewer(String publicationId, String reviewerId) throws JAXBException {
		TUser reviewer = userRepository.getUserByEmail(reviewerId);
		ObjectFactory fac = new ObjectFactory();
		reviewer.getPublicationsForReview().getForReviewID().add(fac.createTUserPublicationsForReviewForReviewID(publicationId));
		System.out.println(userRepository.marshal(reviewer));
		String updatedUser = userRepository.marshal(reviewer);
		userRepository.updateUser(updatedUser, reviewerId);
		
	}
	
	public List<Publication> searchPublications(Map<String, String> searchParams, String status) throws JAXBException, XMLDBException {
		
		List<String> searchKeywords = new ArrayList<String>(searchParams.values());
		String wholePhrase = "";
		for (String s : searchKeywords) {
			wholePhrase = wholePhrase + " " + s;
		}
		wholePhrase = wholePhrase.trim();
		searchKeywords.add(0, wholePhrase);
		
		List<XMLResource> searchResult = null;
		searchResult = publicationRepository.searchPublications(searchKeywords, status);
		
		List<Publication> found = new ArrayList<Publication>();
		
		for (XMLResource res : searchResult) {
			Publication p = publicationRepository.unmarshal(res);
			found.add(p);
		}
			
		return found;
	}
	
	public List<Publication> getPublicationsByStatus(String status) throws JAXBException, XMLDBException {
		List<XMLResource> foundResources = publicationRepository.getPublicationsByStatus(status);
		List<Publication> foundPublications = new ArrayList<Publication>();
		Publication p = null;
		for (XMLResource res : foundResources) {
			p = publicationRepository.unmarshal(res);
			foundPublications.add(p);
		}
		
		
		return foundPublications;
		
	}
	
	
	// TO-DO
	public List<Publication> getMyDocuments(TUser user) {
		List<Publication> found = new ArrayList<Publication>();
		
		// iscupaj iz rdf baze sve authoredBy: user.getUsername() --> vraca id-eve publikacija
		List<String> publicationIDs = new ArrayList<String>();
		
		
		for (String publicationID : publicationIDs) {
			// hendlaj string da bude u formatu kao u bazi:
			// Face_Detection_Recognition.xml
			try {
				Publication p = publicationRepository.getPublication(publicationID);
				found.add(p);
			} catch (XMLDBException e) {
				throw new DocumentNotFoundException("[custom err] Document [" + publicationID + "] not found!\n[original] " + e.getMessage());
			} catch (JAXBException e) {
				throw new UnmarshallingException("[custom err] Unmarshalling publication [" + publicationID + "] failed!\n[original] " + e.getMessage());
				
			} 
			
		}
		
		
		return found;
	}
	
	// TO-DO
	public List<Publication> getDocumentsForReview(TUser user) {
		List<Publication> documentsForReview = new ArrayList<Publication>();
		
		List<String> documentIDs = new ArrayList<String>();
		List<JAXBElement<String>> jaxbList = user.getPublicationsForReview().getForReviewID();
		
		for (JAXBElement<String> jaxbID : jaxbList) {
			documentIDs.add(jaxbID.getValue());
		}
		
		// hendlaj string documentID --> .xml i to sve
		// pitanje je kako ce se cuvati u listi kod USER-a: da li kao
		// ime iz baze ili kao document.title ?
		
		
		for (String docName : documentIDs) {
			try {
				Publication p = publicationRepository.getPublication(docName);
				documentsForReview.add(p);
			} catch (XMLDBException e) {
				throw new DocumentNotFoundException("[custom-err] Document [" + docName + "] not found. \n[original] " + e.getMessage());
			} catch (JAXBException e) {
				throw new UnmarshallingException("[custom-err] Unmarshalling publication [" + docName + "] failed! \n[original] " + e.getMessage());
			} 
			
		}
		
		return documentsForReview;
		
	}
	
	// TO-DO
	public List<Publication> getDocumentsForApproval() {
		// ovo je zapravo search po statusu --> status != ACCEPTED
		
		List<Publication> forApproval = new ArrayList<Publication>();
		
		List<String> statuses = new ArrayList<String>();
		statuses.add("IN_REVIEW");
		statuses.add("UPLOADED");
		statuses.add("REVISED");
		statuses.add("REVIEWED");
		
		// ovde izvuci sve id-eve dokumenata koji imaju ove statuse preko RDF
		List<String> fromRdf = new ArrayList<String>();
		
		for (String documentID : fromRdf) {
			try {
				Publication p = publicationRepository.getPublication(documentID);
				forApproval.add(p);	
			} catch (XMLDBException e) {
				throw new DocumentNotFoundException("[custom-err] Document [" + documentID + "] not found!\n[original-err] " + e.getMessage());
			} catch (JAXBException e) {
				throw new UnmarshallingException("[custom-err] Unmarshalling publication [" + documentID + "] failed!\n[original-err] " + e.getMessage());
			} 
		}
		
		return forApproval;
	}
	

}
