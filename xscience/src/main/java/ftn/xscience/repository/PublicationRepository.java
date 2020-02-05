package ftn.xscience.repository;

import static ftn.xscience.utils.template.XUpdateTemplate.TARGET_NS_PUBLICATION;
import static ftn.xscience.utils.template.XUpdateTemplate.UPDATE;
import static ftn.xscience.utils.template.XUpdateTemplate.XPATH_EXP_CONTAINS;
import static ftn.xscience.utils.template.XUpdateTemplate.XPATH_EXP_STATUS;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.exception.DocumentAlreadyExistsException;
import ftn.xscience.model.publication.Publication;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

@Repository
public class PublicationRepository {

	private static String collectionId = "/db/data/publications";
	
	@Autowired
	BasicXMLConnectionPool connectionPool;
	
	public String save(String documentXml, String documentId) throws XMLDBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		documentId = StringPathHandler.formatPublicationNameForDatabase(documentId);
		try {
			if (DBHandler.documentExists(collectionId, documentId, conn)) {
				throw new DocumentAlreadyExistsException("[custom-err] Document [" + documentId + "] already exists!");
			}
			
			DBHandler.saveDocument(collectionId, documentId, documentXml, conn);
			
			
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return "";
	}
	
	public void updataPublication(String documentXml, String documentId) throws XMLDBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		documentId = StringPathHandler.formatPublicationNameForDatabase(documentId);
		//XMLResource res = null;
		try {
			//res = DBHandler.getDocument(collectionId, documentId, conn);
			DBHandler.deleteDocument(collectionId, documentId, conn);
			DBHandler.saveDocument(collectionId, documentId, documentXml, conn);
			
		} finally {
			connectionPool.releaseConnection(conn);
		}
	}
	
	
	
	public long updatePublicationStatus(String documentId, String toDo) throws XMLDBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		String contextXPath = "/Publication/MetaData/Status/text()";
		long mods = 0;
		try {
			mods = DBHandler.updateXMLResource(conn, collectionId, documentId, TARGET_NS_PUBLICATION, UPDATE, contextXPath, toDo);
			if (toDo.equalsIgnoreCase("ACCEPTED")) {
				contextXPath = "/Publication/MetaData/Accepted/text()";
				String patch = StringPathHandler.formatCurrentDateToString();
				mods += DBHandler.updateXMLResource(conn, collectionId, documentId, TARGET_NS_PUBLICATION, UPDATE, contextXPath, patch);
			}
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		return mods;
	}
	
	public Publication getPublication(String documentId) throws XMLDBException, JAXBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		documentId = StringPathHandler.formatPublicationNameForDatabase(documentId);
		XMLResource res = null;
		Publication ret = null;
		try {
			res = DBHandler.getDocument(collectionId, documentId, conn);
			ret = unmarshal(res);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		return ret;
	}
	
	public List<XMLResource> getPublicationsByStatus(String status) {
		XMLConnectionProperties conn = connectionPool.getConnection();
		List<XMLResource> resourcesFound = null;
		
		try {
			resourcesFound = DBHandler.getResourcesByOneElement(conn, collectionId, TARGET_NS_PUBLICATION, XPATH_EXP_STATUS, status);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		return resourcesFound;
	}
	
	public List<Publication> getAllPublications() {
		XMLConnectionProperties conn = connectionPool.getConnection();
		List<Publication> publicationList = new ArrayList<Publication>();
		List<XMLResource> allPublications = null;
		try {
			allPublications = DBHandler.getAllDocumentsFromCollection(conn, collectionId);
			for (XMLResource res : allPublications) {
				Publication temp = unmarshal(res);
				publicationList.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		return publicationList;
	}
	
	public List<XMLResource> searchPublications(List<String> searchKeywords, String status) {
		XMLConnectionProperties conn = connectionPool.getConnection();
		List<XMLResource> found = null;
		try {
			found = DBHandler.search(conn, collectionId, TARGET_NS_PUBLICATION, XPATH_EXP_CONTAINS, searchKeywords, XPATH_EXP_STATUS, status);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return found;
	}
	
	public ResourceSet getKeywords(String documentId, String xpathQuery) {
		XMLConnectionProperties conn = connectionPool.getConnection();
		ResourceSet keywords = null;
		try {
			keywords = DBHandler.universalXPathQueryResourceSet(conn, collectionId, documentId, TARGET_NS_PUBLICATION, xpathQuery);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return keywords;
	}
	
	
	public String marshal(Publication publication) throws JAXBException {
		OutputStream os = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.publication");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		//ObjectFactory fac = new ObjectFactory();
		//JAXBElement<TPublication> jaxbUser = fac.cre;
        marshaller.marshal(publication, os);

        return os.toString();

	}
	
	public Publication unmarshal(XMLResource resource) throws JAXBException, XMLDBException {
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.publication");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Publication publication = (Publication) JAXBIntrospector.getValue(unmarshaller.unmarshal(resource.getContentAsDOM()));
		return publication;
	}
	
	public Publication unmarshalFromDocument(Document publication) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.publication");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Publication p = (Publication) JAXBIntrospector.getValue(unmarshaller.unmarshal(publication));
		return p;
	}
}
