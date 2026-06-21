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
                        .block();

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

    // Listar todas las ventas con enriquecimiento
    public List<Venta> listarTodas() {
        List<Venta> ventas = ventaRepository.findAll();
        ventas.forEach(this::enriquecerVenta);
        return ventas;
    }

    // Buscar una venta por ID con enriquecimiento
    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id)
                .map(this::enriquecerVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    // Eliminar una venta por ID
    public void eliminarVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
    }

    // Método auxiliar para enriquecer una venta
    private Venta enriquecerVenta(Venta venta) {
        if (venta.getClienteId() != null) {
            try {
                Object cliente = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:9002/api/v1/clientes/" + venta.getClienteId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();
                venta.setDatosCliente(cliente);
            } catch (Exception e) {
                venta.setDatosCliente("Información de cliente no disponible");
            }
        }

        if (venta.getFichaId() != null) {
            try {
                Object ficha = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:9003/api/v1/fichas/" + venta.getFichaId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();
                venta.setDatosFichaVehiculo(ficha);
            } catch (Exception e) {
                venta.setDatosFichaVehiculo("Información de ficha no disponible");
            }
        }

        return venta;
    }
}