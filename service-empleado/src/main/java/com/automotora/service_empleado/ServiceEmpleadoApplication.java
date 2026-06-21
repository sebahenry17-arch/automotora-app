package com.automotora.service_empleado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceEmpleadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceEmpleadoApplication.class, args);
	}

}
