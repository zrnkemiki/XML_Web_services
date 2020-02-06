package ftn.xscience.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.dto.PublicationDTO;
import ftn.xscience.exception.DOMParsingFailedException;
import ftn.xscience.exception.DocumentNotFoundException;
import ftn.xscience.exception.UnmarshallingException;
import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.user.ObjectFactory;
import ftn.xscience.model.user.TUser;
import ftn.xscience.repository.PublicationRepository;
import ftn.xscience.repository.UserRepository;
import ftn.xscience.utils.dom.DOMParser;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.template.RDFManager;
import ftn.xscience.utils.template.XSLTransformator;

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
	
	@Autowired
	UserService userService;
	
	@Autowired
	EMailService emailService;
	
	private RDFManager rdfManager = new RDFManager();
	
	private static String schemaLocation = "WEB-INF/classes/data/xsd/publication.xsd";
	private static String rdfLocation = "WEB-INF/classes/data/gen/publication.rdf";
	private static String grddlLocation = "WEB-INF/classes/data/xsl/grddl.xsl";
	private static String xslPathPublication = "WEB-INF/classes/data/xsl/publication.xsl";
	
	public void savePublication(MultipartFile publicationFile, boolean revisionFlag) {
		String contextPath = context.getRealPath("/");
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		String rdfFilePath = StringPathHandler.handlePathSeparator(rdfLocation, contextPath);
		String grddlFilePath = StringPathHandler.handlePathSeparator(grddlLocation, contextPath);
		String publicationXml = null;
		Document publication = null;
		try {
			publicationXml = new String(publicationFile.getBytes());
			publication = domParser.buildDocument(publicationXml, schemaPath);
		} catch (IOException e) {
			throw new RuntimeException("Error while making string from multipart file in savePublication");
		} catch (SAXException | ParserConfigurationException e) {
			throw new DOMParsingFailedException("[custom-err] Parsing document while savign failed!");
		} 

		String publicationName = publication.getElementsByTagName("Title").item(0).getTextContent() + ".xml";
		
		// ovde se radi update datuma
		// odradi se unmarshal -> marshal kako bi se dobio nazad String celog dokumenta
		// i on se onda prosledi u repository.save()
		
		// ako NIJE revizija
		if (!revisionFlag) {
			publication.getElementsByTagName("Recieved").item(0).setTextContent(StringPathHandler.formatCurrentDateToString());
			publication.getElementsByTagName("Revised").item(0).setTextContent(StringPathHandler.formatNullDateToString());
			publication.getElementsByTagName("Accepted").item(0).setTextContent(StringPathHandler.formatNullDateToString());
			publication.getElementsByTagName("Status").item(0).setTextContent("UPLOADED");
		} else {	
			// ako JESTE revizija
			Map<String, Object> revisedMap = handleRevision(publication, publicationName);
			publication = (Document) revisedMap.get("publication");
			publicationName = (String) revisedMap.get("publicationId");
		}
					
		String publicationXmlDatesUpdated = null;
		try {
			publicationXmlDatesUpdated = publicationRepository.marshal(publicationRepository.unmarshalFromDocument(publication));
			
		} catch (JAXBException e) {
			throw new UnmarshallingException("[custom-err] Unmarshaling document [" + publicationName + "] failed during dates update!");
		}
		
		// extract metadata FIRST
		System.out.println("RDF: " + rdfFilePath);
		try {
			rdfManager.extractMetadata(publicationFile, rdfFilePath, grddlFilePath);
			publicationRepository.save(publicationXmlDatesUpdated, publicationName);
		} catch (IOException | SAXException | TransformerException e) {
			throw new DOMParsingFailedException("Error while extracting METADATA in savePublication");
		} catch (XMLDBException e) {
			throw new DOMParsingFailedException("Error while trying to save publication to exist-db");
		} 
		
	}
	
	public Map<String, Object> handleRevision(Document publication, String oldName) {
		
		publication.getElementsByTagName("Revised").item(0).setTextContent(StringPathHandler.formatCurrentDateToString());
		publication.getElementsByTagName("Status").item(0).setTextContent("REVISED");
		int oldVersion = Integer.parseInt(publication.getDocumentElement().getAttribute("version"));
		int newVersion = oldVersion + 1;
		publication.getDocumentElement().setAttribute("version", String.valueOf(newVersion));
		System.out.println("Old version: " + oldVersion);
		System.out.println("New version: " + newVersion);

		String newVersionString = "-version" + String.valueOf(newVersion);
		String newPublicationName = oldName.split(".xml")[0];
		newPublicationName = newPublicationName + newVersionString + ".xml";
		System.out.println("=====================================");
		System.out.println("New version string: " + newVersionString);
		System.out.println("New publication name" + newPublicationName);
		// samo provera da li ce proci marshall/unmarshall
		try {
			String updatedPublicationXml = publicationRepository.marshal(publicationRepository.unmarshalFromDocument(publication));
		} catch (JAXBException e) {
			throw new UnmarshallingException("[custom-err] Unmarshalling document [" + newPublicationName + "] failed during handling revision");
		} 
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put("publicationId", newPublicationName);
		returnValue.put("publication", publication);
		
		return returnValue;
		
	}
	
	public String getTransformedPublication(String publicationName) {
		String contextPath = context.getRealPath("/");
		String xslPath = StringPathHandler.handlePathSeparator(xslPathPublication, contextPath);
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		
		XSLTransformator transformator = new XSLTransformator();
		
		publicationName = StringPathHandler.formatNameAddXMLInTheEnd(publicationName);
		String xmlPublication = null;
		Publication p = null;
		Document document = null;
		String transformed = null;
		
		
		try {
			p = publicationRepository.getPublication(publicationName);
			xmlPublication = publicationRepository.marshal(p);
			document = domParser.buildDocument(xmlPublication, schemaPath);
			transformed = transformator.generateHTML(document, xslPath);
		} catch (XMLDBException | JAXBException e) {
			throw new RuntimeException("OOps, something went wrong while getting publication in getTransformedPublication");
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			throw new DOMParsingFailedException("Failed while parsing [" + publicationName + "] in getTransformedPublication");
		} 
		
		
		return transformed;
	}
	
	public void acceptPublication(String documentId, TUser loggedUser) throws XMLDBException {
		long mods = publicationRepository.updatePublicationStatus(documentId, "ACCEPTED");
		System.out.println("[INFO] " + mods + " made on document [" + documentId + "]");
		
		prepareAndSendEmailToAuthors(loggedUser, documentId, "ACCEPT", "Your publication [" + documentId + "] is accepted!");
	}
	
	public void withdrawPublication(String documentId) throws XMLDBException {
		long mods = publicationRepository.updatePublicationStatus(documentId, "WITHDRAWN");
		System.out.println("[INFO] " + mods + " made on document [" + documentId + "]");
		
	}
	
	public void rejectPublication(String documentId, TUser loggedUser) throws XMLDBException {
		long mods = publicationRepository.updatePublicationStatus(documentId, "REJECTED");
		System.out.println("[INFO] " + mods + " made on document [" + documentId + "]");
		
		prepareAndSendEmailToAuthors(loggedUser, documentId, "REJECT", "Your publication [" + documentId + "] is reject!");
	}
	
	// prepare for review
	// znaci uklanja se sve o autorima
	// moze se podeliti u 2 dokuemnta gde ce jedan biti samo naslov rada i autori/koautori
	// a drugi dokument samo content rada
	public String publicationAnonymization(String publicationXml) {
		
		return "";
	}
	
	public List<PublicationDTO> anonymize(List<PublicationDTO> dtos) {
		for (PublicationDTO dto : dtos) {
			dto.setAuthor(null);
		}
		return dtos;
	}
	
	public void assignReviewer(String publicationId, String reviewerId, TUser loggedUser) throws JAXBException {
		TUser reviewer = userRepository.getUserByEmail(reviewerId);
		ObjectFactory fac = new ObjectFactory();
		
		publicationId = StringPathHandler.formatNameAddXMLInTheEnd(publicationId);
		reviewer.getPublicationsForReview().getForReviewID().add(fac.createTUserPublicationsForReviewForReviewID(publicationId));
		System.out.println(userRepository.marshal(reviewer));
		String updatedUser = userRepository.marshal(reviewer);
		userRepository.updateUser(updatedUser, reviewerId);
		
		try {
			long mods = publicationRepository.updatePublicationStatus(publicationId, "IN_REVIEW");
			System.out.println("[INFO] " + mods + " made on document [" + publicationId + "]");
		} catch (XMLDBException e) {
			throw new DocumentNotFoundException("[custom-err] Document [" + publicationId + "] not found!");
		} 	
		
		prepareAndSendEmailToReviewer(loggedUser, reviewer, publicationId, "IN_REVIEW", "You have been assigned for a review of publication [" + publicationId + "]");
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
		Map<String, String> sparqlParams = new HashMap<String, String>();
		String author = StringPathHandler.formatUserEmailForSparqlQuery(user.getUsername());
		sparqlParams.put("authoredBy", author);
		
		System.out.println(author);
		
		try {
			publicationIDs = rdfManager.runSPARQL(sparqlParams);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}

		
		for (String publicationID : publicationIDs) {
			// hendlaj string da bude u formatu kao u bazi:
			// Face_Detection_Recognition.xml
			publicationID = publicationID + ".xml";
			System.out.println(publicationID);
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
				docName = StringPathHandler.formatNameAddXMLInTheEnd(docName);
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
		
		//List<String> statuses = new ArrayList<String>();
		//statuses.add("IN_REVIEW");
		//statuses.add("UPLOADED");
		//statuses.add("REVISED");
		//statuses.add("REVIEWED");
		
		String statuses = "\"IN_REVIEW\";\"UPLOADED\";\"REVISED\";\"REVIEWED\"";
		Map<String, String> params = new HashMap<String, String>();
		params.put("status", statuses);
		
		// ovde izvuci sve id-eve dokumenata koji imaju ove statuse preko RDF
		List<String> fromRdf = null;
		try {
			fromRdf = rdfManager.runSPARQL(params);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for (String documentID : fromRdf) {
			try {
				documentID = StringPathHandler.formatNameAddXMLInTheEnd(documentID);
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
	
	public List<Publication> getDocumentsByID(List<String> titles){
		List<Publication> publications = new ArrayList<>();
		for (String title : titles) {
			try {
				title = StringPathHandler.formatNameAddXMLInTheEnd(title);
				Publication p = publicationRepository.getPublication(title);
				publications.add(p);
			} catch (XMLDBException e) {
				// TODO Auto-generated catch block
				throw new DocumentNotFoundException("[custom-err] Document [" + title + "] not found!\n[original] " + e.getMessage());
			} catch (JAXBException e) {
				throw new UnmarshallingException("[custom-err] Unmarshalling publication [" + title + "] failed!\n[original-err] " + e.getMessage());
			}
		}
		return publications;
	}
	
	public void prepareAndSendEmailToAuthors(TUser loggedUser, String documentId, String notificationType, String contentText) {
		Map<String, String> receivers = emailService.getReceiversByPublicationId(documentId);
		String sender = StringPathHandler.generateSenderRecieverForEmail(loggedUser.getPersonalInformation().getName().getFirstName(),
																		loggedUser.getPersonalInformation().getName().getLastName(),
																		loggedUser.getPersonalInformation().getEmail(),
																		loggedUser.getPersonalInformation().getPhoneNumber());
		String subject = "NOTIFICATION: " + notificationType;
		for (String receiver : receivers.keySet()) {
			String content = emailService.generateText(sender, receivers.get(receiver), documentId, notificationType, contentText);
			emailService.sendMail(receiver, subject, content);
		}
	}
	
	public void prepareAndSendEmailToReviewer(TUser loggedUser, TUser reviewer, String documentId, String notificationType, String contentText) {
		String sender = StringPathHandler.generateSenderRecieverForEmail(loggedUser.getPersonalInformation().getName().getFirstName(),
																		loggedUser.getPersonalInformation().getName().getLastName(),
																		loggedUser.getPersonalInformation().getEmail(),
																		loggedUser.getPersonalInformation().getPhoneNumber());
		
		String receiver = StringPathHandler.generateSenderRecieverForEmail(reviewer.getPersonalInformation().getName().getFirstName(),
																		reviewer.getPersonalInformation().getName().getLastName(),
																		reviewer.getPersonalInformation().getEmail(),
																		reviewer.getPersonalInformation().getPhoneNumber());
		
		
		String subject = "NOTIFICATION: " + notificationType;
		
		String content = emailService.generateText(sender, receiver, documentId, notificationType, contentText);
		
		emailService.sendMail(receiver, subject, content);

	}
	

}
