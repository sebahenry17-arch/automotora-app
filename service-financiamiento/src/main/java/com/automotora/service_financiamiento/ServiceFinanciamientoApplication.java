package com.automotora.service_financiamiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceFinanciamientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFinanciamientoApplication.class, args);
	}

}
