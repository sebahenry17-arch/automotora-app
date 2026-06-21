package com.automotora.service_empleado.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.automotora.service_empleado.model.Empleado;
import com.automotora.service_empleado.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> buscarPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(Long id, Empleado empleado) {
        return empleadoRepository.findById(id)
                .map(e -> {
                    e.setNombre(empleado.getNombre());
                    e.setCargo(empleado.getCargo());
                    e.setTelefono(empleado.getTelefono());
                    e.setEmail(empleado.getEmail());
                    return empleadoRepository.save(e);
                })
                .orElseThrow(() -> new RuntimeException("Empleado con ID " + id + " no existe"));
    }

    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}
