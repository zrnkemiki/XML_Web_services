package ftn.xscience.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.model.TUser;
import ftn.xscience.util.template.DocumentHandler;
import ftn.xscience.util.xmldb.BasicXMLConnectionPool;
import ftn.xscience.util.xmldb.XMLConnectionProperties;

@Repository
public class RepoProba {

	@Autowired
	BasicXMLConnectionPool connectionPool;
	
	public void retrieveDocument() throws XMLDBException {
		
		XMLConnectionProperties conn = connectionPool.getConnection();
		XMLResource res = DocumentHandler.getDocument("/db/sample/library", "user.xml", conn);
		System.out.println(res.getContent());
		connectionPool.releaseConnection(conn);
		
	}
	
	public TUser retrieveUser() {
		XMLConnectionProperties conn = connectionPool.getConnection();
		return null;
	}
	

	
}
