package com.automotora.service_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.service_inventario.model.Repuesto;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
   
}
