package ftn.xscience.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import static ftn.xscience.utils.template.XUpdateTemplate.XPATH_EXP_KEYWORDS;

import ftn.xscience.dto.UserDTO;
import ftn.xscience.model.TUser;
import ftn.xscience.repository.PublicationRepository;
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
	PublicationRepository publicationRepository;
	
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

	public List<UserDTO> reviewerSuggestionAlg(String documentId) throws XMLDBException, JAXBException {
		
		String xpathQuery = String.format(XPATH_EXP_KEYWORDS, documentId);
		ResourceSet keywordsSet = null;
		keywordsSet = publicationRepository.getKeywords(documentId, xpathQuery);
		ResourceIterator i = keywordsSet.getIterator();
		List<String> keywords = new ArrayList<String>();
		while (i.hasMoreResources()) {
			keywords.add((String)i.nextResource().getContent());
		}
		
		List<TUser> foundUsers = userRepository.getUsersByExpertise(keywords);
		List<UserDTO> foundUsersDTO = new ArrayList<>();
		
		if(foundUsers.size()!= 0) {
			for (TUser user : foundUsers) {
				UserDTO temp = new UserDTO(user);
				foundUsersDTO.add(temp);
			}
		}
		
		return foundUsersDTO;
		
		
	}
}
