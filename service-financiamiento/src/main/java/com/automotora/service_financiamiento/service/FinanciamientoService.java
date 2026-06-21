package com.automotora.service_financiamiento.service;

import com.automotora.service_financiamiento.model.Financiamiento;
import com.automotora.service_financiamiento.repository.FinanciamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FinanciamientoService {

    @Autowired
    private FinanciamientoRepository financiamientoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Crear nuevo financiamiento
    public Financiamiento guardar(Financiamiento f) {
        validar(f);
        return financiamientoRepository.save(f);
    }

    // Listar todos los financiamientos con datos enriquecidos
    public List<Financiamiento> listarTodos() {
        List<Financiamiento> lista = financiamientoRepository.findAll();
        lista.forEach(this::enriquecerFinanciamiento);
        return lista;
    }

    // Buscar financiamiento por ID con datos enriquecidos
    public Financiamiento buscarPorId(Long id) {
        return financiamientoRepository.findById(id)
                .map(this::enriquecerFinanciamiento)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Financiamiento no encontrado con ID: " + id));
    }

    // Actualizar financiamiento existente
    public Financiamiento actualizar(Long id, Financiamiento nuevo) {
        Financiamiento existente = financiamientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Financiamiento no encontrado con ID: " + id));

        validar(nuevo);

        existente.setTipo(nuevo.getTipo());
        existente.setCuotas(nuevo.getCuotas());
        existente.setMonto(nuevo.getMonto());
        existente.setEstado(nuevo.getEstado());
        existente.setClienteId(nuevo.getClienteId());
        existente.setVentaId(nuevo.getVentaId());

        return financiamientoRepository.save(existente);
    }

    // Eliminar financiamiento
    public void eliminar(Long id) {
        if (!financiamientoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No existe financiamiento con ID: " + id);
        }
        financiamientoRepository.deleteById(id);
    }

    // Validaciones básicas
    private void validar(Financiamiento f) {
        if (f.getCuotas() == null || f.getCuotas() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El número de cuotas debe ser mayor a 0");
        }
        if (f.getMonto() == null || f.getMonto() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El monto debe ser mayor a 0");
        }
    }

    // Enriquecer con datos de otros microservicios
    private Financiamiento enriquecerFinanciamiento(Financiamiento f) {
        try {
            Object cliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:9002/api/v1/clientes/" + f.getClienteId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            f.setDatosCliente(cliente);
        } catch (Exception e) {
            f.setDatosCliente("Información de cliente no disponible");
        }

        try {
            Object venta = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:9004/api/v1/ventas/" + f.getVentaId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            f.setDatosVenta(venta);
        } catch (Exception e) {
            f.setDatosVenta("Información de venta no disponible");
        }

        return f;
    }
}