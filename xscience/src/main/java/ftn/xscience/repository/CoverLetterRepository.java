package ftn.xscience.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.exception.DocumentAlreadyExistsException;
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

}
