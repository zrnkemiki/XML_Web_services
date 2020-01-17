package ftn.xscience.utils.dom;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DocBuilderFactoryConfig {

	@Bean
	public DocBuilderFactory docBuilderFactory() {
		
		return new DocBuilderFactory();
	}
	
	@Bean
	@Primary
	public DocumentBuilderFactory documentBuilderFactory() {
		return docBuilderFactory().getObject();
	}
}
