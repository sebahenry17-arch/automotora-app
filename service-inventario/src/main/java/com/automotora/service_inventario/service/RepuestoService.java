package com.automotora.service_inventario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automotora.service_inventario.model.Repuesto;
import com.automotora.service_inventario.repository.RepuestoRepository;

import java.util.List;

@Service
public class RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Crear/guardar un nuevo repuesto con validación y enriquecimiento
    public Repuesto guardarRepuesto(Repuesto repuesto) {
        // Validar y enriquecer Proveedor
        if (repuesto.getProveedorId() != null) {
            try {
                Object datosProveedor = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:9010/api/v1/proveedores/" + repuesto.getProveedorId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();

                repuesto.setDatosProveedor(datosProveedor);
            } catch (Exception e) {
                throw new RuntimeException("Proveedor no existe con ID: " + repuesto.getProveedorId());
            }
        }

        // Guardar el repuesto
        return repuestoRepository.save(repuesto);
    }

    // Listar todos los repuestos con enriquecimiento
    public List<Repuesto> listarTodos() {
        List<Repuesto> repuestos = repuestoRepository.findAll();
        repuestos.forEach(this::enriquecerRepuesto);
        return repuestos;
    }

    // Buscar un repuesto por ID con enriquecimiento
    public Repuesto buscarPorId(Long id) {
        return repuestoRepository.findById(id)
                .map(this::enriquecerRepuesto)
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado con ID: " + id));
    }

    public Repuesto actualizarRepuesto(Long id, Repuesto repuesto) {
    // Validar existencia
    Repuesto existente = repuestoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Repuesto no encontrado con ID: " + id));

    // Actualizar campos (ejemplo: nombre, cantidadDisponible)
    existente.setNombre(repuesto.getNombre());
    existente.setCantidadDisponible(repuesto.getCantidadDisponible());
    existente.setProveedorId(repuesto.getProveedorId());

    // Enriquecer con datos del proveedor
    return enriquecerRepuesto(repuestoRepository.save(existente));
}

    // Eliminar un repuesto por ID
    public void eliminarRepuesto(Long id) {
        if (repuestoRepository.existsById(id)) {
            repuestoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Repuesto no encontrado con ID: " + id);
        }
    }

    // Método auxiliar para enriquecer un repuesto
    private Repuesto enriquecerRepuesto(Repuesto repuesto) {
        if (repuesto.getProveedorId() != null) {
            try {
                Object proveedor = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:9010/api/v1/proveedores/" + repuesto.getProveedorId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();
                repuesto.setDatosProveedor(proveedor);
            } catch (Exception e) {
                repuesto.setDatosProveedor("Información de proveedor no disponible");
            }
        }
        return repuesto;
    }
}