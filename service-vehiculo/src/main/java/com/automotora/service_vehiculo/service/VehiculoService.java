package com.automotora.service_vehiculo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automotora.service_vehiculo.model.Vehiculo;
import com.automotora.service_vehiculo.repository.VehiculoRepository;

import jakarta.transaction.Transactional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> buscarPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Transactional
    public Vehiculo guardar(Vehiculo vehiculo) {
        // Lógica: si el vehículo viene con ficha, aseguramos que la ficha
        // conozca a su vehículo para que JPA lo guarde bien.
        if (vehiculo.getFicha() != null) {
            vehiculo.getFicha().setVehiculo(vehiculo);
        }
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Long id) {
        vehiculoRepository.deleteById(id);
    }
}