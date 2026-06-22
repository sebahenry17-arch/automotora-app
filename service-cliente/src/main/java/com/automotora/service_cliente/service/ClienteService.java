package com.automotora.service_cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automotora.service_cliente.model.Cliente;
import com.automotora.service_cliente.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente crearCliente(Cliente cliente) {
        
        if (clienteRepository.findByRut(cliente.getRut()) != null) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        
        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new RuntimeException("El correo ya está registrado");
        }

        
        if (!cliente.getTelefono().matches("\\d{9}")) {
            throw new RuntimeException("El teléfono debe tener 9 dígitos");
        }

        return clienteRepository.save(cliente);

        
    }

    // Listar todos los clientes
    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public Cliente actualizar(Long id, Cliente cliente) {
        // 1. Buscar el cliente existente
        Cliente existente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente con ID " + id + " no existe"));

        // 2. Actualizar los campos
        existente.setNombre(cliente.getNombre());
        existente.setRut(cliente.getRut());
        existente.setTelefono(cliente.getTelefono());
        existente.setEmail(cliente.getEmail());
        existente.setHistorialCompras(cliente.getHistorialCompras());

        // 3. Guardar y devolver el actualizado
        return clienteRepository.save(existente);
    }


    // Obtener cliente por ID
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + id + " no existe"));
    }
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
           throw new RuntimeException("Cliente con ID " + id + " no existe");
        }
        clienteRepository.deleteById(id);
    }
}

