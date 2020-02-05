package ftn.xscience.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.exception.DocumentAlreadyExistsException;
import ftn.xscience.model.coverletter.CoverLetter;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

@Repository
public class CoverLetterRepository {

	private static String collectionId = "/db/data/cover-letters";

	@Autowired
	BasicXMLConnectionPool connectionPool;

	public String save(String documentXml, String documentId) throws XMLDBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		documentId = StringPathHandler.formatPublicationNameForDatabase(documentId);
		try {
			if (DBHandler.documentExists(collectionId, documentId, conn)) {
				throw new DocumentAlreadyExistsException("Document [" + documentId + "] already exists!");
			}

			DBHandler.saveDocument(collectionId, documentId, documentXml, conn);

		} finally {
			connectionPool.releaseConnection(conn);
		}

		return "";
	}
	
	public String marshal(CoverLetter coverLetter) throws JAXBException {
		OutputStream os = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.coverletter");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(coverLetter, os);

        return os.toString();
	}
	
	public CoverLetter unmarshal(XMLResource resource) throws JAXBException, XMLDBException {
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.coverletter");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		CoverLetter coverLetter = (CoverLetter) JAXBIntrospector.getValue(unmarshaller.unmarshal(resource.getContentAsDOM()));
		return coverLetter;
	}
	
	public CoverLetter unmarshalFromDocument(Document cl) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.coverletter");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		CoverLetter coverLetter = (CoverLetter) JAXBIntrospector.getValue(unmarshaller.unmarshal(cl));
		return coverLetter;
	}

}
