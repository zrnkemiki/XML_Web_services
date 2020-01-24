package ftn.xscience.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.model.TUser;
import ftn.xscience.repository.ReviewRepository;
import ftn.xscience.repository.UserRepository;
import ftn.xscience.security.JwtValidator;
import ftn.xscience.utils.dom.DOMParser;
import ftn.xscience.utils.dom.StringPathHandler;

@Service
public class ReviewService {

	@Autowired
	ServletContext context;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired 
	JwtValidator validator;
	
	@Autowired
	DOMParser domParser;
	
	private static String schemaLocation = "WEB-INF/classes/data/xsd/review.xsd";
	
	public String saveReview(String reviewXml) throws SAXException, ParserConfigurationException, IOException, XMLDBException {
		String contextPath = context.getRealPath("/");
		
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		
		Document review = domParser.buildDocument(reviewXml, schemaPath);
		
		//String reviewName = review.getElementsByTagName("Title").item(0).getTextContent();
		
		
		// extract metadata FIRST
		
		
		reviewRepository.save(reviewXml, "KONI");
		return "";
	}
	
	// autor dobija nazad review bez navodjenja osobe koja je recenzirala
	public String reviewAnonymization(String reviewXml) {
		return "";
	}
	
	public void declineReview(String token, String publicationId) throws JAXBException {
		
		TUser validUser = validator.validate(token);
		
		TUser reviewer = userRepository.getUserByEmail(validUser.getEmail());
		
		JAXBElement<String> forRemove = null;
	
		for (JAXBElement<String> elem : reviewer.getPublicationsForReview().getForReviewID()) {
			if (elem.getValue().equalsIgnoreCase(publicationId)) {
				forRemove = elem;
				break;
			}
		}
		
		if (forRemove == null) {
			return;
		}
		reviewer.getPublicationsForReview().getForReviewID().remove(forRemove);
		String xmlReviewer = userRepository.marshal(reviewer);
		userRepository.updateUser(xmlReviewer, reviewer.getEmail());
	}
}
