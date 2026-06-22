package com.automotora.service_proveedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceProveedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProveedorApplication.class, args);
	}

}
