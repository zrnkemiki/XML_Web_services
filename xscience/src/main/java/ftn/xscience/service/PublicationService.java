package ftn.xscience.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.repository.PublicationRepository;
import ftn.xscience.utils.dom.DOMParser;
import ftn.xscience.utils.dom.StringPathHandler;

@Service
public class PublicationService {
	
	@Autowired
	ServletContext context;

	@Autowired
	PublicationRepository publicationRepository;
	
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
}
