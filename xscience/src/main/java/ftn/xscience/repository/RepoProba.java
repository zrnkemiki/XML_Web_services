package ftn.xscience.repository;


import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.model.user.TUser;
import ftn.xscience.utils.template.RDFManager;
import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

@Repository
public class RepoProba {

	@Autowired
	BasicXMLConnectionPool connectionPool;
	
	RDFManager rdfManager = new RDFManager();
	
	public void retrieveDocument() throws XMLDBException {
		
		XMLConnectionProperties conn = connectionPool.getConnection();
		XMLResource res = DBHandler.getDocument("/db/sample/library", "user.xml", conn);
		System.out.println(res.getContent());
		connectionPool.releaseConnection(conn);
		
	}
	
	public TUser retrieveUser() {
		XMLConnectionProperties conn = connectionPool.getConnection();
		return null;
	}
	

	public void createCol() {
		XMLConnectionProperties conn = connectionPool.getConnection();
		try {
			DBHandler.getOrCreateCollection("/db/data/users", 0, conn);
			DBHandler.getOrCreateCollection("/db/data/publications", 0, conn);
			DBHandler.getOrCreateCollection("/db/data/cover-letters", 0, conn);
			DBHandler.getOrCreateCollection("/db/data/reviews", 0, conn);
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		
	}
	
	//------------------------METADATA EXTRACT PROBA--------------------------------------
		public void extractMetadata( MultipartFile publicationFile) throws SAXException, IOException, TransformerException {
			

		}
		
		//-----------------------------------SPARQL PROBA----------------------------------------------
		public TUser sparqlProba(String sparqlQuery) throws IOException {
			rdfManager.changeMetaData(null);
			return null;
		}

	
}
