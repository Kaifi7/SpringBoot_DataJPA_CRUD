package com.luv2code.springboot.cruddemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

	 @Bean
	    public Docket api()
	    {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(regex("/.*"))
	                .build().apiInfo(metaData());
	    }
	 	
	 private ApiInfo metaData(){
		 ApiInfo apiInfo = new ApiInfo(
	                "Spring Boot REST API",
	                "Spring Boot REST API for Employees",
	                "1.0",
	                "Terms of service",
	                new Contact("Kaifi The Developer", "https://github.com/Kaifi7", "kafeel1613@gmail.com"),
	               "Apache License Version 2.0",
	                "https://www.apache.org/licenses/LICENSE-2.0");
	        return apiInfo;
		}
}
