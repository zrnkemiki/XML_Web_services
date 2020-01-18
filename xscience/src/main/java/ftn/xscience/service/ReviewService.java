package ftn.xscience.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.repository.ReviewRepository;
import ftn.xscience.utils.dom.DOMParser;
import ftn.xscience.utils.dom.StringPathHandler;

@Service
public class ReviewService {

	@Autowired
	ServletContext context;
	
	@Autowired
	ReviewRepository reviewRepository;
	
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
}
