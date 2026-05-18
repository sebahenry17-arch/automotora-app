package com.automotora.service_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.service_ventas.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta , Long>
{
    
}
