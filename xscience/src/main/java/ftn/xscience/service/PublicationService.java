package ftn.xscience.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.model.ObjectFactory;
import ftn.xscience.model.TUser;
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
	
	public String savePublication(String publicationXml) throws SAXException, ParserConfigurationException, IOException, XMLDBException {
		String contextPath = context.getRealPath("/");
		
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		
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
	

}
