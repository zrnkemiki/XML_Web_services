package ftn.xscience.utils.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


public class XSLTransformator {

private static DocumentBuilderFactory documentFactory;
	
	private static TransformerFactory transformerFactory;
	

	static {

		/* Inicijalizacija DOM fabrike */
		documentFactory = DocumentBuilderFactory.newInstance();
		documentFactory.setNamespaceAware(true);
		documentFactory.setIgnoringComments(true);
		documentFactory.setIgnoringElementContentWhitespace(true);
		
		
		/* Inicijalizacija Transformer fabrike */
		transformerFactory = TransformerFactory.newInstance();
		
	}
 
    /**
     * Creates a PDF using iText Java API
     * @param filePath
     * @throws IOException
     * @throws DocumentException
     */

    
    public String generateHTML(org.w3c.dom.Document document, String xslPath) throws FileNotFoundException {
    	
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	try {

			// Initialize Transformer instance
			StreamSource transformSource = new StreamSource(new File(xslPath));
			Transformer transformer = transformerFactory.newTransformer(transformSource);
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			// Generate XHTML
			transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

			
			// Transform DOM to HTML
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
    	return new String(out.toByteArray());
    
    }
    
    public  void exportHTML_PDF(org.w3c.dom.Document document, String xslPath, String path) throws FileNotFoundException {
    	
    	try {

			// Initialize Transformer instance
			StreamSource transformSource = new StreamSource(new File(xslPath));
			Transformer transformer = transformerFactory.newTransformer(transformSource);
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			String htmlPath = path + ".html";
			String pdfPath = path + ".pdf";
			
			
			// Generate XHTML
			transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

			// Transform DOM to HTML
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileOutputStream(htmlPath));
			transformer.transform(source, result);
			
			// Step 1
	    	Document pdfDocument = new Document();
	        
	    	// Step 2
	        PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfPath));
	        
	        // Step 3
	        pdfDocument.open();
	        
	        // Step 4
	        XMLWorkerHelper.getInstance().parseXHtml(writer, pdfDocument, new FileInputStream(htmlPath));
	        
	        // Step 5
	        pdfDocument.close();
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
    	
    }
    
    }

