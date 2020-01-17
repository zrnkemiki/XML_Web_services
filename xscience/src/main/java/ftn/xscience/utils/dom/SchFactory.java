package ftn.xscience.utils.dom;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;

import org.springframework.beans.factory.FactoryBean;

public class SchFactory implements FactoryBean<SchemaFactory>{

	@Override
	public SchemaFactory getObject() {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return factory;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return SchemaFactory.class;
	}
	
	@Override
	public boolean isSingleton() {
		return true;
	}

}
