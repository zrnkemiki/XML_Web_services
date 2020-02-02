package ftn.xscience.repository;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.xml.transform.TransformerException;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.model.user.TUser;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.template.AuthenticationUtilities;
import ftn.xscience.utils.template.MetadataExtractor;
import ftn.xscience.utils.template.SparqlUtil;
import ftn.xscience.utils.template.AuthenticationUtilities.ConnectionProperties;
import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

@Repository
public class RepoProba {

	@Autowired
	BasicXMLConnectionPool connectionPool;
	

	
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
	
			return null;
		}

	
}
