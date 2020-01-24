package ftn.xscience.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.exception.DocumentAlreadyExistsException;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

import static ftn.xscience.utils.template.XUpdateTemplate.UPDATE;
import static ftn.xscience.utils.template.XUpdateTemplate.TARGET_NS_PUBLICATION;

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
		String contextXPath = "/Publication/MetaData/status/text()";
		long mods = 0;
		try {
			mods = DBHandler.updateXMLResource(conn, collectionId, documentId, TARGET_NS_PUBLICATION, UPDATE, contextXPath, toDo);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		return mods;
	}
}
