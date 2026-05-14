package com.automotora.service_vehiculo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automotora.service_vehiculo.model.FichaVehiculo;
import com.automotora.service_vehiculo.repository.FichaVehiculoRepository;

@RestController
@RequestMapping("/fichas")
public class FichaVehiculoController {

    @Autowired
    private FichaVehiculoRepository fichaRepository;

    @PostMapping("/guardar")
    public FichaVehiculo guardarFicha(@RequestBody FichaVehiculo ficha) {
        return fichaRepository.save(ficha);
    }

    @GetMapping("/listar")
    public List<FichaVehiculo> listarFichas() {
        return fichaRepository.findAll();
    }
}
