package ftn.xscience.utils.xmldb;


import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.exist.xmldb.UserManagementService;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import ftn.xscience.exception.CollectionEmptyException;
import ftn.xscience.exception.DocumentNotFoundException;
import ftn.xscience.utils.dom.StringPathHandler;

public class DBHandler {
	
	
	public static void saveDocument(String collectionId, String documentName, String documentXml, XMLConnectionProperties conn) throws XMLDBException {
		Collection col = null;
		XMLResource res = null;
		UserManagementService ums = null;
		
		try {

			col = getOrCreateCollection(collectionId, 0, conn);
			ums = (UserManagementService)col.getService("UserManagementService", "1.0");
			
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
	
	public static void deleteDocument(String collectionId, String documentName, XMLConnectionProperties conn) throws XMLDBException {
		Collection col = null;
		XMLResource res = null;
		
		try {
			col = DatabaseManager.getCollection(conn.uri + collectionId);
			res = (XMLResource)col.getResource(documentName);
			if (res == null) {
				throw new DocumentNotFoundException("Document [" + documentName + "] not found in collection [" + collectionId + "]!");
			}
			col.removeResource(res);
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
	
	public static Collection getCollection(String collectionUri, XMLConnectionProperties conn) throws XMLDBException {
		Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);
		return col;
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
	
	/*
	 * @conn - konekcija ka bazi
	 * @colleciontId - ime kolekcije
	 * @documentId - ime dokumenta
	 * @targetNamespace - namespace dokumenta koji se updateuje (iz XUpdateTemplate)
	 * @updateCommand - komanda iz XUpdateTamplate - ovde ce biti UPDATE
	 * @contextXPath - path ka elementu koji se updateuje
	 * @patch - zakrpa
	 * 
	 */
	public static long updateXMLResource(XMLConnectionProperties conn, String collectionId, String documentId, String targetNamespace, String updateCommand, String contextXPath, String patch) throws XMLDBException {
		
		// ovo moze baciti XMLDBException pa ga mozda i hvatati odmah
		Collection col = null;
		long mods = 0;
		try {
			col = getCollection(collectionId, conn);
			
			col.setProperty("indent", "yes");
			XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
	        xupdateService.setProperty("indent", "yes");
	        
	        String xupdateCommand = String.format(updateCommand, targetNamespace, contextXPath, patch);
	        System.out.println("[INFO: ]  " + xupdateCommand);
	        
	        mods = xupdateService.updateResource(documentId, xupdateCommand);	

		} finally {
			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException e) {
					e.printStackTrace();
				}
			}
		}
		return mods;
	}
	
	public static XMLResource storeXMLResource(XMLConnectionProperties conn, String collectionId, String documentId, String xmlContent) {
		Collection col = null;
		XMLResource res = null;
		
		documentId = StringPathHandler.formatEmailStringForDatabase(documentId);
		//UserManagementService ums = null;
		try {
			col = DatabaseManager.getCollection(conn.uri + collectionId);
			res = (XMLResource)col.getResource(documentId);
			//ums = (UserManagementService)col.getService("UserManagementService", "1.0");
			
			//ums.chmod(res, 504);;
			
			res.setContent(xmlContent);
			col.storeResource(res);
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (res != null) {
				try {
					((EXistResource)res).freeResources();
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
				
		
		return null;
	}
	
	public static List<XMLResource> getAllDocumentsFromCollection(XMLConnectionProperties conn, String collectionId) throws XMLDBException {
		ArrayList<XMLResource> resources = new ArrayList<XMLResource>();
		
		String[] resourceIds = null;
		Collection col = null;
		XMLResource res = null;
		try {
			col = getCollection(collectionId, conn);
			col.setProperty(OutputKeys.INDENT, "yes");
			
			
			resourceIds = col.listResources();
			
			for (String id : resourceIds) {
				res = (XMLResource)col.getResource(id);
				resources.add(res);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return resources;
	}
	
	public static List<XMLResource>	search(XMLConnectionProperties conn, String collectionId, String targetNamespace, String xpathCommand, List<String> searchKeywords) {
		
		/* xpathCommand - npr onaj moj XPATH_EXP_CONTAINS -> 
		 * arg1 = ime dokumenta iz baze (znaci sve zajedno sa .xml)
		 * arg2 = string koji se pretrazuje u dokumentu
		 * keyword je taj string koji se trazi u dokumentu
		 */
		
		Collection col = null;
		XMLResource res = null;
		String[] documentNames = null;
		String xpathExp = null;
		List<XMLResource> resources = new ArrayList<XMLResource>();
		ResourceSet result = null;
		
		try {
			col = getCollection(collectionId, conn);
			col.setProperty(OutputKeys.INDENT, "yes");
			
			XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");
            
            // make the service aware of namespaces, using the default one
            xpathService.setNamespace("", targetNamespace);
            
            
            documentNames = col.listResources();
            for (String docName : documentNames) {
            	
            	res = (XMLResource)col.getResource(docName);
            	
            	// idemo redom -> prva rec je cela fraza
            	// ako je nadje, odma prelazi na sledeci dokument
            	
            	for (int i = 0; i < searchKeywords.size(); i++) {
            		result = null;
            		// u slucaju da fraza nije pronadjena
            		// znaci moraju biti sve pojedinacno pronadjene
            		// inace je pretraga neuspesna
            		
            		xpathExp = String.format(xpathCommand, docName, searchKeywords.get(i).toLowerCase());
            		// execute xpath expression 
                    System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
    				result = xpathService.query(xpathExp);
    				    				
    				// trazi se cela fraza
    				if (i == 0) {
    					// pronadjena je -> sledeci dokument
    					if (result.getSize() != 0) {
    						resources.add(res);
    						break;
    					// nije pronadjena -> trazi pojedinacne reci	
    					} else {
    						continue;
    					}
    					// pojedinacne reci
    				} else if (i == searchKeywords.size()-1 && result.getSize() != 0) {
       					// ako je pronasao sve reci -> dodaj resurs
    					resources.add(res);
    				} else {
    					// jedna rec nije pronadjena --> odma idemo sledeci dokument
    					if (result.getSize() == 0) {
    						break;
    					}  					
    				}	
            	}
			}
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resources;
		
	}

}
