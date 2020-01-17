package ftn.xscience.util.template;

import java.io.File;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.exception.DocumentNotFoundException;
import ftn.xscience.util.xmldb.XMLConnectionProperties;

public class DocumentHandler {
	
	
	public static void saveDocument(String collectionId, String documentName, String documentXml, XMLConnectionProperties conn) throws XMLDBException {
		Collection col = null;
		XMLResource res = null;

		try {

			col = getOrCreateCollection(collectionId, 0, conn);
			res = (XMLResource) col.createResource(documentName, XMLResource.RESOURCE_TYPE);
			res.setContent(documentXml);
			col.storeResource(res);

		} finally {

			// don't forget to cleanup
			if (res != null) {
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}

			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	 
	}
	
	public static XMLResource getDocument(String collectionId, String documentId, XMLConnectionProperties conn) throws XMLDBException {
		Collection col = null;
		XMLResource res = null;
		
		
		try {
            // get the collection
        	System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(conn.uri + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");
            
            System.out.println("[INFO] Retrieving the document: " + documentId);
            res = (XMLResource)col.getResource(documentId);
            
            if(res == null) {
                System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
                throw new DocumentNotFoundException("Document [" + documentId + "] cannot be found!");
            } 
            
		} finally {
	           if(res != null) {
	                try { 
	                	((EXistResource)res).freeResources(); 
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
	            
	            if(col != null) {
	                try { 
	                	col.close(); 
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
		}
		
		return res;
	}
	
	
	public static boolean documentExists(String collectionId, String documentId, XMLConnectionProperties conn) throws XMLDBException {
		Collection col = null;
		XMLResource res = null;

		try {
            col = DatabaseManager.getCollection(conn.uri + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");
            
            res = (XMLResource)col.getResource(documentId);
            
            if(res == null) {
                System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
                return false;
            } 
            
		} finally {
	           if(res != null) {
	                try { 
	                	((EXistResource)res).freeResources(); 
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
	            
	            if(col != null) {
	                try { 
	                	col.close(); 
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
		}

		return true;
		
	}
	
	
	
	public static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset, XMLConnectionProperties conn) throws XMLDBException {
        Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);
        
        // create the collection if it does not exist
        if(col == null) {
        
         	if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }
            
        	String pathSegments[] = collectionUri.split("/");
            
        	if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();
            
                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }
                
                Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);
                
                if (startCol == null) {
                	
                	// child collection does not exist
                    
                	String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);
                    
                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");
                    
                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);
                    
                    col.close();
                    parentCol.close();
                    
                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset, conn);
        } else {
            return col;
        }

	}

}
