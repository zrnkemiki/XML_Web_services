package ftn.xscience.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.model.TUser;
import ftn.xscience.util.template.DocumentHandler;
import ftn.xscience.util.xmldb.BasicXMLConnectionPool;
import ftn.xscience.util.xmldb.XMLConnectionProperties;

@Repository
public class UserRepository {

	@Autowired
	BasicXMLConnectionPool connectionPool;
	
	private static String collectionId = "/db/sample/library";
	
	
	public TUser getUserByEmail(String email) throws JAXBException {
		String documentId = "user-" + email.toLowerCase() + ".xml";
		documentId.replace("@", "%40");
		System.out.println("=====================");
		System.out.println(documentId);
		XMLConnectionProperties conn = connectionPool.getConnection();
		TUser user = null;
		
		try {
			XMLResource res = DocumentHandler.getDocument(collectionId, "user-editor%40gmail.com.xml", conn);
			user = unmarshal(res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(conn);
		}

		return user;
	}
	
	
	public String marshal(TUser user) throws JAXBException {
		OutputStream os = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(user, os);

        return os.toString();

	}
	
	public TUser unmarshal(XMLResource resource) throws JAXBException, XMLDBException {
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TUser user = (TUser) JAXBIntrospector.getValue(unmarshaller.unmarshal(resource.getContentAsDOM()));
		
		return user;
	}
}
