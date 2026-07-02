package com.automotora.service_inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceInventarioApplication.class, args);
	}

}
