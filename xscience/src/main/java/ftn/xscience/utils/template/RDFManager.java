package ftn.xscience.utils.template;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.transform.TransformerException;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.pfunction.library.listIndex;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.template.AuthenticationUtilities.ConnectionProperties;

public class RDFManager {

	
	
	public static final String PRED_PATH = "<https://www.xscience.com/data/publication/predicate/";
	public static final String XML_LITERAL = "^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral>.";
	public static final String SPARQL_NAMED_GRAPH_URI = "/example/sparql/metadata";

	public void extractMetadata(MultipartFile publicationFile, String rdfFilePath) throws IOException, SAXException, TransformerException {
		
		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		
		String SPARQL_NAMED_GRAPH_URI = "/example/sparql/metadata";


		
		
		
		
		InputStream inputStream =  new BufferedInputStream(publicationFile.getInputStream());
		
		MetadataExtractor metadataExtractor = new MetadataExtractor();
		

		
		metadataExtractor.extractMetadata(inputStream, new FileOutputStream(new File(rdfFilePath)));
		
		Model model = ModelFactory.createDefaultModel();
		
		model.read(rdfFilePath);
	
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		model.write(out, SparqlUtil.NTRIPLES);

		

		// Writing the named graph
	
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI, new String(out.toByteArray()));
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();

	}

	private static String makeORStatement(Map.Entry<String, String> entry) {

		String[] values = entry.getValue().split(";");
		String orStatement = "{?publication " + PRED_PATH + entry.getKey() + ">" + values[0] + XML_LITERAL + "}";

		for (int i = 1; i < values.length; i++) {
			orStatement += "UNION" + "{" + "?publication " + PRED_PATH + entry.getKey() + "> " + values[i] + XML_LITERAL
					+ "}";
		}

		return orStatement;
	}
	
	private static String makeDateFilterStatement(Map.Entry<String, String> entry) {
		String dateStatement = "";
		return dateStatement;
	}

	private static String publicationSPARQLBuilder(Map<String, String> params) {
		String sparqlStart = "SELECT * FROM <%s> WHERE {";
		String sparqlQuery = "";
		String logicalOperator = "";

		for (Map.Entry<String, String> entry : params.entrySet()) {

			if (entry.getValue().contains(";")) {
				sparqlQuery += makeORStatement(entry);

			} else if (entry.getValue().contains("!")) {
				//Ako je prvi metapodatak negacija
				if(sparqlQuery.equals("")) {
					sparqlQuery = "?publication " + PRED_PATH + entry.getKey() + "> " + "?" + entry.getKey();
				}
				sparqlQuery += " MINUS { ?publication" + PRED_PATH + entry.getKey() + "> " + entry.getValue().substring(1)
						+ XML_LITERAL + "}";

			} else if(entry.getKey().contains("date")) {
				
			}else {
				sparqlQuery += "?publication " + PRED_PATH + entry.getKey() + "> " + entry.getValue() + XML_LITERAL;
			}

		}

		sparqlQuery = sparqlStart + sparqlQuery + "}";
		return sparqlQuery;
	}
	
	public void runSPARQL(Map<String, String> params) throws IOException {
		String sparqlQuery = publicationSPARQLBuilder(params);
		
		
		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		//String sparqlFilePath = "D:\\XML_Web_services\\xscience\\src\\main\\resources\\data\\sparql\\query1.rq";

		// Querying the named graph with a referenced SPARQL query
		//System.out.println("[INFO] Loading SPARQL query from file \"" + sparqlFilePath + "\"");
		//String sparqlQuery = String.format(FileUtil.readFile(sparqlFilePath, StandardCharsets.UTF_8),
		//		conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI);

		sparqlQuery = String.format(sparqlQuery, conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI);
		System.out.println(sparqlQuery);

		// Create a QueryExecution that will access a SPARQL service over HTTP
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the SPARQL endpoint, iterate over the result set...
		System.out.println("[INFO] Showing the results for SPARQL query using the result handler.\n");
		ResultSet results = query.execSelect();

		String varName;
		RDFNode varValue;

		while (results.hasNext()) {

			// A single answer from a SELECT query
			QuerySolution querySolution = results.next();
			Iterator<String> variableBindings = querySolution.varNames();

			// Retrieve variable bindings
			while (variableBindings.hasNext()) {

				varName = variableBindings.next();
				varValue = querySolution.get(varName);

				System.out.println(varName + ": " + varValue);
			}
			System.out.println();
		}

		// Issuing the same query once again...

		// Create a QueryExecution that will access a SPARQL service over HTTP
		query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the collection, dump output response as XML
		System.out.println("[INFO] Showing the results for SPARQL query in native SPARQL XML format.\n");
		results = query.execSelect();
		
		// ResultSetFormatter.outputAsXML(System.out, results);
		ResultSetFormatter.out(System.out, results);

		query.close();

		System.out.println("[INFO] End.");
	}
}
