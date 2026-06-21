package com.automotora.service_ventas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automotora.service_ventas.model.Venta;
import com.automotora.service_ventas.repository.VentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Crear/guardar una nueva venta con validación y enriquecimiento
    public Venta guardarVenta(Venta venta) {
        // Validar y enriquecer Cliente
        if (venta.getClienteId() != null) {
            try {
                Object datosCliente = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:9002/api/v1/clientes/" + venta.getClienteId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block(); // Bloqueamos para asegurar la validación antes del save

                venta.setDatosCliente(datosCliente);
            } catch (Exception e) {
                throw new RuntimeException("Cliente no existe con ID: " + venta.getClienteId());
            }
        }

        // Validar y enriquecer FichaVehiculo
        if (venta.getFichaId() != null) {
            try {
                Object datosFicha = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:9003/api/v1/fichas/" + venta.getFichaId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();

                venta.setDatosFichaVehiculo(datosFicha);
            } catch (Exception e) {
                throw new RuntimeException("FichaVehiculo no existe con ID: " + venta.getFichaId());
            }
        }

        // Guardar la venta si ambas validaciones pasan
        return ventaRepository.save(venta);
    }

    // Listar todas las ventas
    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    // Buscar una venta por ID
    public Optional<Venta> buscarPorId(Long id) {
        return ventaRepository.findById(id);
    }

    // Eliminar una venta por ID
    public void eliminarVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
    }
}