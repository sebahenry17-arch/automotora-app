package com.automotora.service_mantenimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceMantenimientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceMantenimientoApplication.class, args);
	}

}
