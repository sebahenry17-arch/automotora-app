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

@RestController
@RequestMapping("/vehiculos/tipos")
public class TipoVehiculoController {

    @Autowired
    private TipoVehiculoRepository repository;

    @GetMapping
    public List<TipoVehiculo> listar() {
        return repository.findAll();
    }

    @PostMapping
    public TipoVehiculo crear(@RequestBody TipoVehiculo tipo) {
        return repository.save(tipo);
    }
}
