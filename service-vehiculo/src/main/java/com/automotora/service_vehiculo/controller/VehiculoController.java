package com.automotora.service_vehiculo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automotora.service_vehiculo.model.Vehiculo;
import com.automotora.service_vehiculo.service.VehiculoService;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // 🔹 Listar todos los vehículos
    @GetMapping
    public List<Vehiculo> listar() {
        return vehiculoService.listarVehiculos();
    }

    // 🔹 Obtener vehículo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtener(@PathVariable Long id) {
        return vehiculoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Crear nuevo vehículo
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevo = vehiculoService.crearVehiculo(vehiculo);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 Eliminar vehículo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Conteo simple por tipo de vehículo
    @GetMapping("/conteo")
    public List<Object[]> conteoPorTipoVehiculo() {
        return vehiculoService.conteoPorTipoVehiculo();
    }
}
