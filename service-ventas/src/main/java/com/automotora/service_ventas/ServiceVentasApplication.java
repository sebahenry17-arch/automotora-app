package com.automotora.service_ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceVentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceVentasApplication.class, args);
	}

}
