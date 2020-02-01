package ftn.xscience.repository;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.xml.transform.TransformerException;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.model.user.TUser;
import ftn.xscience.utils.template.AuthenticationUtilities;
import ftn.xscience.utils.template.FileUtil;
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
		public void extractMetadata() throws SAXException, IOException, TransformerException {
			ConnectionProperties conn = AuthenticationUtilities.loadProperties();
			String SPARQL_NAMED_GRAPH_URI = "/example/sparql/metadata";
			System.out.println("[INFO] ");

			// Referencing XML file with RDF data in attributes
			String xmlFilePath = "D:\\XML_Web_services\\xscience\\src\\main\\resources\\data\\xml\\publication3.xml";

			String rdfFilePath = "D:\\XML_Web_services\\xscience\\src\\main\\resources\\data\\gen\\publication3.rdf";

			// Automatic extraction of RDF triples from XML file
			MetadataExtractor metadataExtractor = new MetadataExtractor();
			System.out.println("[INFO] Extracting metadata from RDFa attributes...");
			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)),
					new FileOutputStream(new File(rdfFilePath)));
			

			// Loading a default model with extracted metadata
			Model model = ModelFactory.createDefaultModel();
			model.read(rdfFilePath);

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			model.write(out, SparqlUtil.NTRIPLES);

			System.out.println("[INFO] Extracted metadata as RDF/XML...");
			model.write(System.out, SparqlUtil.RDF_XML);

			// Writing the named graph
			System.out.println("[INFO] Populating named graph \"" + SPARQL_NAMED_GRAPH_URI + "\" with extracted metadata.");
			String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI,
					new String(out.toByteArray()));
			System.out.println(sparqlUpdate);

			// UpdateRequest represents a unit of execution
			UpdateRequest update = UpdateFactory.create(sparqlUpdate);

			UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
			processor.execute();

			// Read the triples from the named graph
			System.out.println();
			System.out.println("[INFO] Retrieving triples from RDF store.");
			System.out.println("[INFO] Using \"" + SPARQL_NAMED_GRAPH_URI + "\" named graph.");

			System.out.println("[INFO] Selecting the triples from the named graph \"" + SPARQL_NAMED_GRAPH_URI + "\".");
			String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI, "?s ?p ?o");

			// Create a QueryExecution that will access a SPARQL service over HTTP
			QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

			// Query the collection, dump output response as XML
			ResultSet results = query.execSelect();

			ResultSetFormatter.out(System.out, results);

			query.close();

			System.out.println("[INFO] End.");

		}
		
		//-----------------------------------SPARQL PROBA----------------------------------------------
		public TUser sparqlProba(String sparqlQuery) throws IOException {
	
			return null;
		}

	
}
