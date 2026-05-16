package com.automotora.service_vehiculo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.automotora.service_vehiculo.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

       List<Vehiculo> findByMarca(String marca);

    // Buscar por modelo y año
    List<Vehiculo> findByModeloAndAño(String modelo, int año);

    // Reporte: conteo de vehículos por tipo
    @Query("""
           SELECT v.tipoVehiculo.nombre AS tipoVehiculo,
                  COUNT(v) AS cantidad
           FROM Vehiculo v
           GROUP BY v.tipoVehiculo.nombre
           """)
    List<Object[]> conteoPorTipoVehiculo();

   
    // 🚗 Vehículos con tipo cargado (evita nombre = null)
    @Query("""
           SELECT v FROM Vehiculo v
           JOIN FETCH v.tipoVehiculo
           """)
       List<Vehiculo> findAllConTipoVehiculo();
}