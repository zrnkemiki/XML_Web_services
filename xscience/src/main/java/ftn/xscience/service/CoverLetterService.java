package ftn.xscience.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.repository.CoverLetterRepository;
import ftn.xscience.utils.dom.DOMParser;
import ftn.xscience.utils.dom.StringPathHandler;

@Service
public class CoverLetterService {

	@Autowired
	ServletContext context;
	
	@Autowired
	CoverLetterRepository coverLetterRepository;
	
	@Autowired
	DOMParser domParser;
	
	private static String schemaLocation = "WEB-INF/classes/data/xsd/coverLetter.xsd";
	
	public String saveCoverLetter(String coverLetterXml) throws SAXException, ParserConfigurationException, IOException, XMLDBException {
		String contextPath = context.getRealPath("/");
		
		String schemaPath = StringPathHandler.handlePathSeparator(schemaLocation, contextPath);
		
		Document coverLetter = domParser.buildDocument(coverLetterXml, schemaPath);
		
		String coverLetterName = coverLetter.getElementsByTagName("PublicationTitle").item(0).getTextContent() + ".xml";
		
		
		// extract metadata FIRST
		
		
		coverLetterRepository.save(coverLetterXml, coverLetterName);
		return "";
	}
}
