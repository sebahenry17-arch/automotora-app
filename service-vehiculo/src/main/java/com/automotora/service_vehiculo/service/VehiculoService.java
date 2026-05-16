package com.automotora.service_vehiculo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automotora.service_vehiculo.model.Vehiculo;
import com.automotora.service_vehiculo.repository.VehiculoRepository;



@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        // Validaciones de negocio
        if (vehiculo.getMarca() == null || vehiculo.getMarca().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }
        if (vehiculo.getModelo() == null || vehiculo.getModelo().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío");
        }
        if (vehiculo.getAño() < 1900 || vehiculo.getAño() > 2100) {
            throw new IllegalArgumentException("El año debe estar entre 1900 y 2100");
        }
        if (vehiculo.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        return vehiculoRepository.save(vehiculo);
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAllConTipoVehiculo();
    }

    public List<Object[]> conteoPorTipoVehiculo() {
        return vehiculoRepository.conteoPorTipoVehiculo();
    }
    
    public Optional<Vehiculo> buscarPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    public void eliminar(Long id) {
        vehiculoRepository.deleteById(id);
    }
}