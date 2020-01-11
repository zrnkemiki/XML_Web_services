package ftn.xscience.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ftn.xscience.util.xmldb.BasicXMLConnectionPool;
import ftn.xscience.util.xmldb.XMLConnectionProperties;

@Configuration
public class XMLConnectionPoolConfiguration {

	@Bean
	public BasicXMLConnectionPool basicXMLConnectionPool() {
		BasicXMLConnectionPool connectionPool = new BasicXMLConnectionPool();
		List<XMLConnectionProperties> newPool = new ArrayList<XMLConnectionProperties>();
		for (int i = 0; i < connectionPool.getInitPoolSize(); i++) {
			newPool.add(connectionPool.createConnection());
		}	
		connectionPool.setConnectionPool(newPool);

		return connectionPool;
	}
	
	@Bean
	public XMLConnectionProperties xmlConnectionProperties() {
		return basicXMLConnectionPool().createConnection();
	}
}
