package com.automotora.service_vehiculo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.service_vehiculo.model.TipoVehiculo;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long> {

    // Buscar por nombre (ej: "Sedán", "SUV", "Camioneta")
    TipoVehiculo findByNombre(String nombre);

    // Buscar por nombre ignorando mayúsculas/minúsculas
    TipoVehiculo findByNombreIgnoreCase(String nombre);
}
