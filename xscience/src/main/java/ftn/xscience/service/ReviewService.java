package ftn.xscience.service;

import static ftn.xscience.utils.template.XUpdateTemplate.XPATH_EXP_KEYWORDS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.dto.UserDTO;
import ftn.xscience.exception.DOMParsingFailedException;
import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.review.Review;
import ftn.xscience.model.user.TUser;
import ftn.xscience.repository.PublicationRepository;
import ftn.xscience.repository.ReviewRepository;
import ftn.xscience.repository.UserRepository;
import ftn.xscience.security.JwtValidator;
import ftn.xscience.utils.dom.DOMParser;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.template.RDFManager;
import ftn.xscience.utils.template.XSLTransformator;

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
	PublicationService publicationService;
	
	@Autowired 
	JwtValidator validator;
	
	@Autowired
	DOMParser domParser;
	
	
	private static String schemaLocation = "WEB-INF/classes/data/xsd/review.xsd";
	private static String xslPathReview = "WEB-INF/classes/data/xsl/review.xsl";
	
	public void saveReviewFromObject(Review review) throws JAXBException, SAXException, ParserConfigurationException, IOException, XMLDBException {
		
		String reviewXml = reviewRepository.marshal(review);
		System.out.println("==================");
		System.out.println(reviewXml);
		
		saveReview(reviewXml);
				
	}
	
	public void saveReview(String reviewXml) throws SAXException, ParserConfigurationException, IOException, XMLDBException, JAXBException {
		String contextPath = context.getRealPath("/");
		
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		
		Document review = domParser.buildDocument(reviewXml, schemaPath);
		
		String reviewName = "review-" + review.getElementsByTagName("PublicationTitle").item(0).getTextContent() + ".xml";
		
		reviewName = StringPathHandler.formatPublicationNameForDatabase(reviewName);
		boolean exists = reviewRepository.checkExistance(reviewName);
		if (exists) {
			reviewName.replace("review-", "review-2-");
		}
		
		// extract metadata FIRST		
		String documentId = review.getElementsByTagName("PublicationTitle").item(0).getTextContent();
		documentId = StringPathHandler.formatNameAddXMLInTheEnd(documentId);
		Publication p = publicationRepository.getPublication(documentId);
		String about = p.getAbout();
		RDFManager rdfManager = new RDFManager();
		Map<String, String> metadataMap = new HashMap<String, String>();
		metadataMap.put("subject", reviewName);
		metadataMap.put("predicate", "publicationTitle");
		metadataMap.put("object", about);
		rdfManager.addNewReviewMetaData(metadataMap);
		
		reviewRepository.save(reviewXml, reviewName);
	}
	
	public String getTransformedReview(String reviewId) {
		String contextPath = context.getRealPath("/");
		String xslPath = StringPathHandler.handlePathSeparator(xslPathReview, contextPath);
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		
		XSLTransformator transformator = new XSLTransformator();
		
		reviewId = StringPathHandler.formatNameAddXMLInTheEnd(reviewId);
		String xmlReview = null;
		Review r = null;
		Document document = null;
		String transformed = null;
		
		try {
			r = reviewRepository.getReview(reviewId);
			xmlReview = reviewRepository.marshal(r);
			document = domParser.buildDocument(xmlReview, schemaPath);
			transformed = transformator.generateHTML(document, xslPath);
		} catch (XMLDBException | JAXBException e) {
			throw new RuntimeException("Ooops, something went wrong while getting review in getTransformedReview");
		} catch (SAXException | ParserConfigurationException | IOException e) {
			throw new DOMParsingFailedException("Failed while parsing [" + reviewId + "] in getTransformedReview");
		}
		
		return transformed;
	}
	
	// TO-DO --> MERGE
	public void mergeReviews(String publicationId) throws XMLDBException {
		// merge & change status of publication to REVIEWED
		
		long mods = publicationRepository.updatePublicationStatus(publicationId, "REVIEWED");
		System.out.println("[INFO] " + mods + " made on document [" + publicationId + "]");
	}
	
	// autor dobija nazad review bez navodjenja osobe koja je recenzirala
	public String reviewAnonymization(String reviewXml) {
		return "";
	}
	
	public void declineReview(String token, String publicationId) throws JAXBException {
		
		TUser validUser = validator.validate(token);
		
		TUser reviewer = userRepository.getUserByEmail(validUser.getUsername());
		
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
		userRepository.updateUser(xmlReviewer, reviewer.getUsername());
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
