package com.automotora.service_financiamiento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automotora.service_financiamiento.model.Financiamiento;

@Service
public class FinanciamientoService {

    @Autowired
    private FinanciamientoRepository financiamientoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Crear financiamiento
    public Financiamiento guardarFinanciamiento(Financiamiento financiamiento) {
        // Validar existencia de Cliente
        if (financiamiento.getClienteId() != null) {
            Object cliente = webClientBuilder.build()
                .get()
                .uri("http://localhost:9002/clientes/" + financiamiento.getClienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            financiamiento.setDatosCliente(cliente);
        }

        // Validar existencia de Venta
        if (financiamiento.getVentaId() != null) {
            Object venta = webClientBuilder.build()
                .get()
                .uri("http://localhost:9004/ventas/" + financiamiento.getVentaId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            financiamiento.setDatosVenta(venta);
        }

        return financiamientoRepository.save(financiamiento);
    }

    // Obtener financiamiento enriquecido
    public Financiamiento obtenerFinanciamiento(Long id) {
        return financiamientoRepository.findById(id)
                .map(this::enriquecerFinanciamiento)
                .orElse(null);
    }

    // Listar todos
    public List<Financiamiento> listarFinanciamientos() {
        return financiamientoRepository.findAll();
    }

    // Eliminar
    public void eliminarFinanciamiento(Long id) {
        financiamientoRepository.deleteById(id);
    }

    // Método privado para enriquecer con datos externos
    private Financiamiento enriquecerFinanciamiento(Financiamiento f) {
        if (f.getClienteId() != null) {
            Object cliente = webClientBuilder.build()
                .get()
                .uri("http://localhost:9002/clientes/" + f.getClienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            f.setDatosCliente(cliente);
        }

        if (f.getVentaId() != null) {
            Object venta = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/ventas/" + f.getVentaId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            f.setDatosVenta(venta);
        }

        return f;
    }
}
