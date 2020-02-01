package ftn.xscience.modelTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

@Repository
public class TestRepo {

	@Autowired
	BasicXMLConnectionPool pool;
	
	private String userCollectionId = "/db/data/users";
	private String publCollectionId = "/db/data/publications";
	private String clCollectionId = "/db/data/cover-letters";
	private String publicationId = "Conceptualizing_Location-One_Term_Many_Meanings.xml";
	private String clId = "cl-Conceptualizing_Location-One_Term_Many_Meanings.xml";
	private String userId = "user-author-gmail.com.xml";
	
	public XMLResource getPubl() {
		XMLConnectionProperties conn = pool.getConnection();
		XMLResource res = null;
		try {
			res = DBHandler.getDocument(publCollectionId, publicationId, conn);
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.releaseConnection(conn);
		}
		
		return res;
	}
	
	public XMLResource getUser() {
		XMLConnectionProperties conn = pool.getConnection();
		XMLResource res = null;
		try {
			res = DBHandler.getDocument(userCollectionId, userId, conn);
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.releaseConnection(conn);
		}
		
		return res;

	}
	
	public XMLResource getCL() {
		XMLConnectionProperties conn = pool.getConnection();
		XMLResource res = null;
		try {
			res = DBHandler.getDocument(clCollectionId, clId, conn);
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.releaseConnection(conn);
		}
		
		return res;

	}
	
	
}
