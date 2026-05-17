package com.automotora.service_ficha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automotora.service_ficha.model.FichaVehiculo;

public interface FichaVehiculoRepository extends JpaRepository<FichaVehiculo, Long> {
    
    List<FichaVehiculo> findByVehiculoId(Long vehiculoId);
}
