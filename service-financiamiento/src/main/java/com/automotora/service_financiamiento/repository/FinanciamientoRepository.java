package com.automotora.service_financiamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.service_financiamiento.model.Financiamiento;

@Repository
public interface FinanciamientoRepository extends JpaRepository<Financiamiento, Long> {
    
}
