package ftn.xscience.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import static ftn.xscience.utils.template.XUpdateTemplate.TARGET_NS_USER;
import static ftn.xscience.utils.template.XUpdateTemplate.XPATH_EXP_EXPERTISE;
import ftn.xscience.model.user.ObjectFactory;
import ftn.xscience.model.user.TUser;
import ftn.xscience.utils.dom.StringPathHandler;
import ftn.xscience.utils.xmldb.BasicXMLConnectionPool;
import ftn.xscience.utils.xmldb.DBHandler;
import ftn.xscience.utils.xmldb.XMLConnectionProperties;

@Repository
public class UserRepository {

	@Autowired
	BasicXMLConnectionPool connectionPool;
	
	private static String collectionId = "/db/data/users";
	
	
	public TUser getUserByEmail(String email) throws JAXBException {
		
		String documentId = StringPathHandler.formatEmailStringForDatabase(email);
		XMLConnectionProperties conn = connectionPool.getConnection();
		TUser user = null;
		
		try {
			XMLResource res = DBHandler.getDocument(collectionId, documentId, conn);
			user = unmarshal(res);
		
		} catch (XMLDBException e) {
			
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(conn);
		}

		return user;
	}
	
	public void updateUser(String xmlUser, String documentId) {
		XMLConnectionProperties conn = connectionPool.createConnection();
		
		try {
			DBHandler.storeXMLResource(conn, collectionId, documentId, xmlUser);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
	}
	
	public List<TUser> getUsersByExpertise(List<String> keywords) throws JAXBException, XMLDBException {
		XMLConnectionProperties conn = connectionPool.createConnection();
		List<XMLResource> usersList = null;
		List<TUser> users = new ArrayList<TUser>();
		TUser u = null;
		try {
			usersList = DBHandler.universalSearch(conn, collectionId, TARGET_NS_USER, XPATH_EXP_EXPERTISE, keywords);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		
		if (usersList != null) {
			for (XMLResource res : usersList) {
				u = null;
				u = unmarshal(res);
				if (u != null) {
					users.add(u);
				}
			}
		}
		
		return users;
	}
	
	
	public String marshal(TUser user) throws JAXBException {
		OutputStream os = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.user");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		ObjectFactory fac = new ObjectFactory();
		JAXBElement<TUser> jaxbUser = fac.createUser(user);
        marshaller.marshal(jaxbUser, os);

        return os.toString();

	}
	
	public TUser unmarshal(XMLResource resource) throws JAXBException, XMLDBException {
		JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.user");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TUser user = (TUser) JAXBIntrospector.getValue(unmarshaller.unmarshal(resource.getContentAsDOM()));
		return user;
	}
}
