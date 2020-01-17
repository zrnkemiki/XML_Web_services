package ftn.xscience.utils.dom;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.FactoryBean;

public class DocBuilderFactory implements FactoryBean<DocumentBuilderFactory> {


	
	@Override
	public DocumentBuilderFactory getObject() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setNamespaceAware(true);
		return factory;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return DocumentBuilderFactory.class;
	}
	
    @Override
    public boolean isSingleton() {
        return true;
    }

	
}
