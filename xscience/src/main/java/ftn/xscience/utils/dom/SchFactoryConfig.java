package ftn.xscience.utils.dom;

import javax.xml.validation.SchemaFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchFactoryConfig {

	@Bean
	public SchFactory schFactory() {
		return new SchFactory();
	}
	
	@Bean
	public SchemaFactory schemaFactory() {
		return schFactory().getObject();
	}
}
