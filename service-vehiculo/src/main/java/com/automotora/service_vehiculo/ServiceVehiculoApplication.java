package com.automotora.service_vehiculo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = { 
    org.springdoc.core.configuration.SpringDocHateoasConfiguration.class 
})
public class ServiceVehiculoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVehiculoApplication.class, args);
    }

    
}
