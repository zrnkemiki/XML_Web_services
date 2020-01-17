package ftn.xscience.utils.dom;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ftn.xscience.exception.DOMParsingFailedException;

@Component
public class DOMParser {

	@Autowired
	DocumentBuilderFactory documentFactory;
	@Autowired
	SchemaFactory schemaFactory;
	
	
	public Document buildDocument(String xml, String schemaLocation) throws SAXException, ParserConfigurationException, IOException {
		documentFactory.setSchema(schemaFactory.newSchema(new File(schemaLocation)));
		
		DocumentBuilder builder = documentFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xml)));
		System.out.println("Ovo je dokument parsiran u kontroleru:");
		
		
		return doc;
	}
	
	public void validateDocument(Document doument, String schemaLocation) {
		// ipak validacija je gore zbog factory
	}
	
	public Document buildStringDoc(File file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder builder = documentFactory.newDocumentBuilder();
		Document doc = builder.parse(file);
		return doc;
	}
}
