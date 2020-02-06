package ftn.xscience.utils.template;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ftn.xscience.utils.template.AuthenticationUtilities.ConnectionProperties;

public class RDFManager {

	public static final String PRED_PATH = "<https://www.xscience.com/data/publication/predicate/";
	public static final String XML_LITERAL = "^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral>.";
	public static final String XS_DATE = "^^xs:date";
	public static final String USER_PATH = "<https://www.xscience.com/data/users/";
	public static final String PUB_PATH = "<https://www.xscience.com/data/publications/";
	public static final String PUBLICATION_NAMED_GRAPH_URI = "/publication/metadata";
	public static final String REVIEW_NAMED_GRAPH_URI = "/review/metadata";
	

	public void extractMetadata(MultipartFile publicationFile, String rdfFilePath, String grddlFilePath) throws IOException, SAXException, TransformerException {
		
		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		
		InputStream inputStream =  new BufferedInputStream(publicationFile.getInputStream());
		
		MetadataExtractor metadataExtractor = new MetadataExtractor();
		
		
		metadataExtractor.extractMetadata(inputStream, new FileOutputStream(new File(rdfFilePath)), grddlFilePath);
		Model model = ModelFactory.createDefaultModel();
		
		model.read(rdfFilePath);
	
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		model.write(out, SparqlUtil.NTRIPLES);
		model.write(System.out, SparqlUtil.RDF_XML);

		// Writing the named graph
	
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + PUBLICATION_NAMED_GRAPH_URI, new String(out.toByteArray()));
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();

	}

	public void addNewPubMetaData(Map<String, String> params) throws IOException {
		
		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		String pred = PRED_PATH.substring(1);
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", pred);
		
		
		Resource resource = model.createResource(params.get("subject"));
		Property property = model.createProperty(pred, params.get("predicate"));
		Literal literal;
		if(params.get("type").equals("date")) {
			literal = model.createTypedLiteral(params.get("object"), "http://www.w3.org/2001/XMLSchema#date");
		}else {
			literal = model.createLiteral(params.get("object"), true);
		}

		Statement statement = model.createStatement(resource, property, literal);
		
		model.add(statement);
		
		System.out.println("[INFO] Rendering the UPDATE model as RDF/XML...");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);		
		
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + PUBLICATION_NAMED_GRAPH_URI, new String(out.toByteArray()));
		
		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		// UpdateProcessor sends update request to a remote SPARQL update service. 
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();
	}
	
		
	public void changeMetaData(Map<String, String> newParams, Map<String, String> oldParams) throws IOException {	

		deleteSPARQL(oldParams);
		this.addNewPubMetaData(newParams);
	}
	
	
	private static void deleteSPARQL(Map<String, String> params) throws IOException {

		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		String type = "";
		if(params.get("type").equals("date")) {
			type = XS_DATE;
		}else {
			type = XML_LITERAL;
		}
		
		String deleteSparql = "DELETE DATA { GRAPH <" + conn.dataEndpoint + PUBLICATION_NAMED_GRAPH_URI 
				+ "> {" + PUB_PATH + params.get("subject") + ">" + PRED_PATH + params.get("predicate") + "> " + params.get("object") + type + "}}";
				
		UpdateRequest update = UpdateFactory.create(deleteSparql);
		
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
		String dateStatement = "{?publication " + PRED_PATH + entry.getKey() + ">" + " ?" + entry.getKey() + " FILTER(?" + entry.getKey();
		String[] values = entry.getValue().split(":");;
			
			switch(values[0]) {
			  case "lt":
			    dateStatement += " <= " + values[1] + XS_DATE + ")}";
			    break;
			  case "gt":
				  dateStatement += " >= " + values[1] + XS_DATE + ")}";
			    break;
			  case "eq":
			      dateStatement += " = " + values[1] + XS_DATE + ")}";
			      break;
			  default:
				  dateStatement += " >= " + values[0] + XS_DATE + " && " + "?" + entry.getKey()  + " <= " + values[1] + XS_DATE + ")}" ;
			
			}
		
		return dateStatement;
	}

	private static String publicationSPARQLBuilder(Map<String, String> params) {
		String sparqlStart = "PREFIX xs: <http://www.w3.org/2001/XMLSchema#> SELECT ?publication FROM <%s> WHERE {";
		String sparqlQuery = "";

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

			} else if(entry.getValue().contains("$")) {
				String value = entry.getValue().substring(1);
				entry.setValue(value);
				sparqlQuery += makeDateFilterStatement(entry);
				
			}else if(entry.getValue().contains("@")){
				String value = entry.getValue().split("@")[1];
				sparqlQuery += "?publication " + PRED_PATH + entry.getKey() + "> " + USER_PATH + value.substring(1,value.length()-1) + ">";
			}else {
				sparqlQuery += "?publication " + PRED_PATH + entry.getKey() + "> " + entry.getValue() + XML_LITERAL;
			}

		}

		sparqlQuery += "}";
		sparqlQuery = sparqlStart + sparqlQuery;
		return sparqlQuery;
	}
	
	public List<String> runSPARQL(Map<String, String> params) throws IOException {
		
		String sparqlQuery = publicationSPARQLBuilder(params);
		List<String> resultList = new ArrayList<String>();
		
		ConnectionProperties conn = AuthenticationUtilities.loadProperties();

		sparqlQuery = String.format(sparqlQuery, conn.dataEndpoint + PUBLICATION_NAMED_GRAPH_URI);
		System.out.println(sparqlQuery);
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		ResultSet results = query.execSelect();

		String varName;
		RDFNode varValue;
		String[] strValue;

		while (results.hasNext()) {

			// A single answer from a SELECT query
			QuerySolution querySolution = results.next();
			Iterator<String> variableBindings = querySolution.varNames();

			// Retrieve variable bindings
			while (variableBindings.hasNext()) {
				
				varName = variableBindings.next();
				varValue = querySolution.get(varName);
				
				strValue = varValue.toString().split("/");
				resultList.add(strValue[strValue.length-1]);
			}
		}

		// Issuing the same query once again...

		query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		results = query.execSelect();
		
		// ResultSetFormatter.outputAsXML(System.out, results);
		ResultSetFormatter.out(System.out, results);

		query.close();

		return resultList;
	}
	
	public void addNewReviewMetaData(Map<String, String> params) throws IOException {
		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		String pred = PRED_PATH.substring(1);
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", pred);
		
		
		Resource resource = model.createResource(params.get("subject"));
		Property property = model.createProperty(pred, params.get("predicate"));
		Literal literal = model.createLiteral(params.get("object"));

		Statement statement = model.createStatement(resource, property, literal);
		
		model.add(statement);
		
		System.out.println("[INFO] Rendering the UPDATE model as RDF/XML...");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);		
		
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + REVIEW_NAMED_GRAPH_URI, new String(out.toByteArray()));
		
		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		// UpdateProcessor sends update request to a remote SPARQL update service. 
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();
	}
	
	public List<String> reviewSPARQL(String publication) throws IOException{
		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		
		List<String> resultList = new ArrayList<String>();
		String sparqlQuery = "SELECT ?review FROM <%s> WHERE {?review " + PRED_PATH + "publicationTitle> " + publication + "}";
		
		sparqlQuery = String.format(sparqlQuery, conn.dataEndpoint + REVIEW_NAMED_GRAPH_URI);
		System.out.println(sparqlQuery);
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		ResultSet results = query.execSelect();

		String varName;
		RDFNode varValue;
		String[] strValue;

		while (results.hasNext()) {

			// A single answer from a SELECT query
			QuerySolution querySolution = results.next();
			Iterator<String> variableBindings = querySolution.varNames();

			// Retrieve variable bindings
			while (variableBindings.hasNext()) {
				
				varName = variableBindings.next();
				varValue = querySolution.get(varName);
				
				strValue = varValue.toString().split("/");
				resultList.add(strValue[strValue.length-1]);
			}
		}
		
		return resultList;
	}

}
