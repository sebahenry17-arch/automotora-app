package com.automotora.service_cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceClienteApplication.class, args);
	}

}
