package com.automotora.service_vehiculo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.automotora.service_vehiculo.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Buscar por marca y modelo
    Vehiculo findByMarcaAndModelo(String marca, String modelo);

    // Reporte: conteo de vehículos por tipo
    @Query("""
           SELECT v.tipoVehiculo.nombre AS tipoVehiculo,
                  COUNT(v) AS cantidad
           FROM Vehiculo v
           GROUP BY v.tipoVehiculo.nombre
           """)
    List<Object[]> conteoPorTipoVehiculo();

    // Vehículos con ficha técnica asociada
    @Query("""
           SELECT v FROM Vehiculo v
           JOIN FETCH v.fichaVehiculo f
           WHERE f IS NOT NULL
           """)
    List<Vehiculo> findVehiculosConFichaCompleta();
}