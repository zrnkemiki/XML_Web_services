package ftn.xscience.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.exception.UnmarshallingException;
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
		
		String coverLetterName = coverLetter.getElementsByTagName("PublicationTitle").item(0).getTextContent();
		
		coverLetter.getDocumentElement().setAttribute("submissionDate", StringPathHandler.formatCurrentDateToString());
		coverLetter.getDocumentElement().setAttribute("id", "idvalue0");
		
		coverLetterName = StringPathHandler.formatCLNameForDatabase(coverLetterName);
		
		String coverLetterDateUpdated = null;
		try {
			coverLetterDateUpdated = coverLetterRepository.marshal(coverLetterRepository.unmarshalFromDocument(coverLetter));
		} catch (Exception e) {
			throw new UnmarshallingException("Unmarshaling document [" + coverLetterName + "] failed!");
		}
		
		coverLetterRepository.save(coverLetterDateUpdated, coverLetterName);
		//coverLetterRepository.save(coverLetterXml, coverLetterName);
		return "";
	}
}
