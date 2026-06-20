package com.automotora.service_vehiculo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automotora.service_vehiculo.model.TipoVehiculo;
import com.automotora.service_vehiculo.repository.TipoVehiculoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/vehiculo/tipos")
@Tag(name = "Tipos de Vehículos", description = "Operaciones relacionadas con los tipos de vehículos")
public class TipoVehiculoController {

    @Autowired
    private TipoVehiculoRepository repository;  

    @Operation(summary = "Listar tipos de vehículos", description = "Devuelve todos los tipos de vehículos registrados")
    @GetMapping
    public List<TipoVehiculo> listar() {
        return repository.findAll();
    }

    @Operation(summary = "Crear tipo de vehículo", description = "Agrega un nuevo tipo de vehículo")
    @PostMapping
    public TipoVehiculo crear(@RequestBody TipoVehiculo tipo) {
        return repository.save(tipo);
    }
}