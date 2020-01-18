package ftn.xscience.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.exception.DocumentAlreadyExistsException;
import ftn.xscience.util.template.DocumentHandler;
import ftn.xscience.util.xmldb.BasicXMLConnectionPool;
import ftn.xscience.util.xmldb.XMLConnectionProperties;
import ftn.xscience.utils.dom.StringPathHandler;

@Repository
public class CoverLetterRepository {

	private static String collectionId = "/db/data/cover-letters";

	@Autowired
	BasicXMLConnectionPool connectionPool;

	public String save(String documentXml, String documentId) throws XMLDBException {
		XMLConnectionProperties conn = connectionPool.getConnection();
		documentId = StringPathHandler.formatPublicationNameForDatabase(documentId);
		try {
			if (DocumentHandler.documentExists(collectionId, documentId, conn)) {
				throw new DocumentAlreadyExistsException("Document [" + documentId + "] already exists!");
			}

			DocumentHandler.saveDocument(collectionId, documentId, documentXml, conn);

		} finally {
			connectionPool.releaseConnection(conn);
		}

		return "";
	}

}
