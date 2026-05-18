package com.automotora.service_ventas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automotora.service_ventas.model.Venta;
import com.automotora.service_ventas.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Guardar una venta validando Cliente y FichaVehiculo
    public Venta guardarVenta(Venta venta) {
        // Validación de fecha
        if (venta.getFecha() == null || venta.getFecha().isAfter(LocalDate.now())) {
            throw new RuntimeException("La fecha de la venta es inválida");
        }

        // Validación de monto
        if (venta.getMonto() == null || venta.getMonto() <= 0) {
            throw new RuntimeException("El monto de la venta debe ser mayor a 0");
        }

        // Validación de existencia de Cliente
        if (venta.getClienteId() != null) {
            Object datosCliente = webClientBuilder.build()
                .get()
                .uri("http://localhost:9002/clientes/" + venta.getClienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();

            if (datosCliente == null) {
                throw new RuntimeException("Cliente no encontrado");
            }
            venta.setDatosCliente(datosCliente);
        }

        // Validación de existencia de FichaVehiculo
        if (venta.getFichaId() != null) {
            Object datosFicha = webClientBuilder.build()
                .get()
                .uri("http://localhost:9003/fichas/" + venta.getFichaId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();

            if (datosFicha == null) {
                throw new RuntimeException("FichaVehiculo no encontrada");
            }
            venta.setDatosFichaVehiculo(datosFicha);
        }

        // Guardar venta
        return ventaRepository.save(venta);
    }

    // Obtener venta enriquecida
    public Venta obtenerVentaCompleta(Long id) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        if (venta != null) {
            return enriquecerVenta(venta);
        }
        return null;
    }

    // Listar todas las ventas
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    //Eliminar venta
    public void eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada");
        }
        ventaRepository.deleteById(id);
    }
    private Venta enriquecerVenta(Venta venta) {
        if (venta.getClienteId() != null) {
            try {
                Object cliente = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:9002/clientes/" + venta.getClienteId())
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
                    .uri("http://localhost:9003/fichas/" + venta.getFichaId())
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