package ftn.xscience.modelTest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import ftn.xscience.model.coverletter.CoverLetter;

//import ftn.xscience.modelPubl.Publication;
//import ftn.xscience.modelUser.ObjectFactory;
//import ftn.xscience.modelUser.TUser;

import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.user.ObjectFactory;
import ftn.xscience.model.user.TUser;

@RestController
public class TestController {

	
	@Autowired
	TestRepo repo;
	
	@GetMapping(value = "/p/test")
	public ResponseEntity<?> test() {
		XMLResource res = null;
		XMLResource u = null;
/*		res = repo.getRes();
		u = repo.getUser();
		if (res == null) {
			System.out.println("res je NULL u kontroleru");
		}
		if (u == null) {
			System.out.println("user je null u kontroleru");
		}
		
		
		
		Publication p = unmarshal(res);
		System.out.println("==============PODACI (unmarshall) ===============");
		System.out.println(p.getLanguage());
		System.out.println(p.getTitle());
		System.out.println(p.getMetaData().getFieldOfStudy());
		
		String marshaled = marshal(p);
		System.out.println("=============PODACI (marshalled) ==============");
		System.out.println(marshaled);
		
		TUser us = unmarshalUser(u);
		System.out.println("========== podaci USER unmarshalled");
		System.out.println(us.getEmail());
		System.out.println(us.getExpertises().getExpertise().get(0));
		
		String marshUser = marshalUser(us);
		System.out.println(marshUser);
*/		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/p/real")
	public ResponseEntity<?> realTest() {
		XMLResource publ = repo.getPubl();
		XMLResource usr = repo.getUser();
		XMLResource cl = repo.getCL();
		Publication p = unmarshalPublication(publ);
		System.out.println("========== UNMARSHALING PUBLICATION ==============");
		System.out.println(p.getLanguage());
		System.out.println(p.getTitle().getValue());
		System.out.println(p.getMetaData().getFieldOfStudy().getValue());
		System.out.println(p.getAuthor().get(0).getEmail());
		System.out.println(p.getAbstract().getContentOrQuote().get(0).toString());
		
		String marshaledPub = marshalPublication(p);
		System.out.println("========== MARSHALLED PUBLICATION ================");
		System.out.println(marshaledPub);
		
		TUser u = unmarshalUser(usr);
		System.out.println("=========== UNMARSHALING USER ==================");
		System.out.println(u.getUsername());
		System.out.println(u.getExpertises().getExpertise().get(0));
		
		String marshaledUser = marshalUser(u);
		System.out.println("=========== MARSHALED USER ================");
		System.out.println(marshaledUser);
		
		CoverLetter c = unmarshalCL(cl);
		System.out.println("========== UNMARSHALING CL ==============");
		System.out.println(c.getEditor());
		System.out.println(c.getPublicationTitle());
		System.out.println(c.getAuthor().getEmail());
		System.out.println(c.getContent().getCLSection().get(0).toString());
		
		String marshaledCl = marshalCL(c);
		System.out.println("=========== MARSHALED CL =================");
		System.out.println(marshaledCl);
		
		try {
			cl.setContent(marshaledCl);
		} catch (Exception e) {
			System.out.println("wopa sori");
			e.printStackTrace();
		}
		
		System.out.println("============= unmarshall AGAIN ;)))=====================");
		CoverLetter c2 = unmarshalCL(cl);
		System.out.println(c2.getEditor());
		System.out.println(c2.getAuthor().getEmail());
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	
	
	public String marshalPublication(Publication p) {
		OutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.publication");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(p, os);
		} catch (JAXBException e) {
			System.out.println("Usao u catch od marshal publication");
			e.printStackTrace();
		} 
		return os.toString();
	}
	
	public String marshalUser(TUser u) {
		OutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.user");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ObjectFactory fc = new ObjectFactory();
			JAXBElement<TUser> jaxbu = fc.createUser(u);
			
			marshaller.marshal(jaxbu, os);
		} catch (Exception e) {
			System.out.println("usao u catch od marshal usera");
			e.printStackTrace();
		}
		
		return os.toString();
	}
	
	public String marshalCL(CoverLetter c) {
		OutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.coverletter");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(c, os);
		} catch (Exception e) {
			System.out.println("usao u catch od marshal CL");
			e.printStackTrace();
		}
		return os.toString();
	}
	
	public Publication unmarshalPublication(XMLResource res) {
		Publication p = null;
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.publication");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			//JAXBElement<Publication> root = unmarshaller.unmarshal(res.getContentAsDOM(), Publication.class);
			p = (Publication)JAXBIntrospector.getValue(unmarshaller.unmarshal(res.getContentAsDOM()));
			//p = root.getValue();
		} catch (JAXBException | XMLDBException e) {
			System.out.println("usao u catch od unmarshal publication");
			e.printStackTrace();
		} 
		if (p == null) {
			System.out.println("Publication je jos uvek null! (u unmarshal publication)");
		}
		return p;
	}
	
	public TUser unmarshalUser(XMLResource res) {
		TUser u = null;
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.user");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			//JAXBElement<TUser> root = unmarshaller.unmarshal(res.getContentAsDOM(), TUser.class);
			u = (TUser)JAXBIntrospector.getValue(unmarshaller.unmarshal(res.getContentAsDOM()));
			//u = root.getValue();
		} catch (Exception e) {
			System.out.println("usao u catch od unmarshal usera");
			e.printStackTrace();
		}
		
		if (u == null) {
			System.out.println("user je null (u unmarshal user)");
		}
		return u;
	}
	
	public CoverLetter unmarshalCL(XMLResource res) {
		CoverLetter cl = null;
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.model.coverletter");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			cl = (CoverLetter)JAXBIntrospector.getValue(unmarshaller.unmarshal(res.getContentAsDOM()));
		} catch (Exception e) {
			System.out.println("usao u catch od unmarshal CL");
			e.printStackTrace();
		}
		
		if (cl == null) {
			System.out.println("CL je null (u unmarshall cl)");
		}
		return cl;
	}

	
	
	
	
	
	
	
	
	
	
	
	
/*	
	public String marshal(Publication p) {
		OutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.modelPubl");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(p, os);
		} catch (JAXBException e) {
			System.out.println("Usao u catch od marshal");
			e.printStackTrace();
		} 
		
		return os.toString();
	}
	
	public String marshalUser(TUser u) {
		OutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.modelUser");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ObjectFactory fc = new ObjectFactory();
			JAXBElement<TUser> jaxbu = fc.createUser(u);
			
			marshaller.marshal(jaxbu, os);
		} catch (Exception e) {
			System.out.println("catch u marshal usera");
			e.printStackTrace();
		}
		
		return os.toString();
	}
	
	public Publication unmarshal(XMLResource res) {
		Publication p = null;
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.modelPubl");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			//JAXBElement<Publication> root = unmarshaller.unmarshal(res.getContentAsDOM(), Publication.class);
			p = (Publication)JAXBIntrospector.getValue(unmarshaller.unmarshal(res.getContentAsDOM()));
			//p = root.getValue();
		} catch (JAXBException | XMLDBException e) {
			System.out.println("usao u catch od unmarshal");
			e.printStackTrace();
		} 
		if (p == null) {
			System.out.println("Publication je jos uvek null!");
		}
		return p;
	}
	
	public TUser unmarshalUser(XMLResource res) {
		TUser u = null;
		try {
			JAXBContext context = JAXBContext.newInstance("ftn.xscience.modelUser");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			//JAXBElement<TUser> root = unmarshaller.unmarshal(res.getContentAsDOM(), TUser.class);
			u = (TUser)JAXBIntrospector.getValue(unmarshaller.unmarshal(res.getContentAsDOM()));
			//u = root.getValue();
		} catch (Exception e) {
			System.out.println("unmarshal user error");
			e.printStackTrace();
		}
		
		if (u == null) {
			System.out.println("user je null");
		}
		return u;
	}
	
	*/
}
