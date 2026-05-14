package com.automotora.service_vehiculo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.service_vehiculo.model.FichaVehiculo;
import com.automotora.service_vehiculo.model.Vehiculo;

@Repository
public interface FichaVehiculoRepository extends JpaRepository<FichaVehiculo, Long> {

    // Buscar ficha por vehículo 
    FichaVehiculo findByVehiculo(Vehiculo vehiculo);

    // Buscar ficha por ID de vehículo 
    FichaVehiculo findByVehiculoId(Long vehiculoId);
}
