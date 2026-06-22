package com.automotora.service_proveedor.service;

import com.automotora.service_proveedor.model.Proveedor;
import com.automotora.service_proveedor.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    
    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    
    public Proveedor detalle(Long id) {
        return proveedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    
    public Proveedor crear(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

   public Proveedor actualizar(Long id, Proveedor proveedor) {
    Proveedor existente = proveedorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

    // ✅ Estos métodos ahora existen gracias a Lombok
    existente.setNombre(proveedor.getNombre());
    existente.setRutDni(proveedor.getRutDni());
    existente.setTelefono(proveedor.getTelefono());
    existente.setEmail(proveedor.getEmail());
    existente.setDireccion(proveedor.getDireccion());

    return proveedorRepository.save(existente);
}

    // 🔹 Eliminar
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}