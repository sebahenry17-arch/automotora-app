package com.automotora.service_ficha.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automotora.service_ficha.model.FichaVehiculo;
import com.automotora.service_ficha.service.FichaVehiculoService;

@RestController
@RequestMapping("/fichas")
public class FichaVehiculoController {

    @Autowired
    private FichaVehiculoService service;

    // Listar todas las fichas
    @GetMapping
    public List<FichaVehiculo> listarFichas() {
        return service.listarFichas();
    }

    // Buscar ficha por ID
    @GetMapping("/{id}")
    public FichaVehiculo buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    // Listar fichas de un vehículo específico
    @GetMapping("/vehiculo/{vehiculoId}")
    public List<FichaVehiculo> buscarPorVehiculoId(@PathVariable Long vehiculoId) {
        return service.buscarPorVehiculoId(vehiculoId);
    }

    // Crear nueva ficha
    @PostMapping
    public FichaVehiculo crearFicha(@RequestBody FichaVehiculo ficha) {
        return service.guardarFicha(ficha);
    }

    // Actualizar ficha existente
    @PutMapping("/{id}")
    public FichaVehiculo actualizarFicha(@PathVariable Long id, @RequestBody FichaVehiculo ficha) {
        ficha.setId(id);
        return service.actualizarFicha(ficha);
    }

    // Eliminar ficha
    @DeleteMapping("/{id}")
    public void eliminarFicha(@PathVariable Long id) {
        service.eliminarFicha(id);
    }

    //ficha + datos del vehículo
    @GetMapping("/{id}/detalles")
    public Map<String, Object> obtenerFichaConVehiculo(@PathVariable Long id) {
        return service.obtenerFichaConVehiculo(id);
    }
}