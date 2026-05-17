package com.automotora.service_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.service_cliente.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    
    Cliente findByRut(String rut);

    Cliente findByEmail(String email);

    Cliente findByNombreIgnoreCase(String nombre);
}
