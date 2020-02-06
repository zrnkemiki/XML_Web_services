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
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.exception.DocumentAlreadyExistsException;
import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.review.Review;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

@Repository
public class ReviewRepository {

	private static String collectionId = "/db/data/reviews";

	@Autowired
	BasicXMLConnectionPool connectionPool;

	public void save(String documentXml, String documentId) throws XMLDBException {
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

	}
	
	public Review getReview(String reviewId) throws XMLDBException, JAXBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		reviewId = StringPathHandler.formatPublicationNameForDatabase(reviewId);
		XMLResource res = null;
		Review review = null;
		try {
			res = DBHandler.getDocument(collectionId, reviewId, conn);
			review = unmarshal(res);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return review;
	}
	
	public boolean checkExistance(String documentId) throws XMLDBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		boolean flag = false;
		try {
			flag = DBHandler.documentExists(collectionId, documentId, conn);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		return flag;
	}
	
	public String marshal(Review review) throws JAXBException {
		OutputStream os = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.review");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		//ObjectFactory fac = new ObjectFactory();
		//JAXBElement<TPublication> jaxbUser = fac.cre;
        marshaller.marshal(review, os);

        return os.toString();

	}
	
	public Review unmarshal(XMLResource resource) throws JAXBException, XMLDBException {
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.review");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Review review = (Review) JAXBIntrospector.getValue(unmarshaller.unmarshal(resource.getContentAsDOM()));
		return review;
	}
}
