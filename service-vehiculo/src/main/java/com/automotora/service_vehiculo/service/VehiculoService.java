package com.automotora.service_vehiculo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automotora.service_vehiculo.model.Vehiculo;
import com.automotora.service_vehiculo.repository.VehiculoRepository;

import io.micrometer.common.lang.NonNull;
import jakarta.transaction.Transactional;



@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listarVehiculos() {
        // Si tu repo tiene un método custom con JOIN a TipoVehiculo
        return vehiculoRepository.findAllConTipoVehiculo();
        // Si no, simplemente:
        // return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> buscarPorId(@NonNull Long id) {
        return vehiculoRepository.findById(id);
    }

    @Transactional
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        // Validaciones básicas (puedes moverlas a la entidad con Bean Validation)
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
    @Transactional
        public Vehiculo actualizarVehiculo(Long id, Vehiculo vehiculo) {
            Vehiculo existente = vehiculoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

    // Validaciones básicas
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

    // Actualizar campos
    existente.setMarca(vehiculo.getMarca());
    existente.setModelo(vehiculo.getModelo());
    existente.setAño(vehiculo.getAño());
    existente.setStock(vehiculo.getStock());
    existente.setTipoVehiculo(vehiculo.getTipoVehiculo());

    return vehiculoRepository.save(existente);
}

    @Transactional
    public void eliminar(@NonNull Long id) {
        vehiculoRepository.deleteById(id);
    }

    public List<Object[]> conteoPorTipoVehiculo() {
        return vehiculoRepository.conteoPorTipoVehiculo();
    }
}