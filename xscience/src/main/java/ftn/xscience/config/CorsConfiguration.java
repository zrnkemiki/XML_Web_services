package ftn.xscience.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
	/*
	@SuppressWarnings("deprecation")
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
		    @Override
		    public void addCorsMappings(CorsRegistry registry) {
		        registry.addMapping("/**").allowedOrigins("http://localhost:4200");
		    }
        };
    }
	*/
	
	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**").allowedMethods("PUT", "DELETE", "POST", "GET").allowedOrigins("http://localhost:4200");
	        
	    }
	    
	    
}