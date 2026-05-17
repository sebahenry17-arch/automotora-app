package com.automotora.service_ficha.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automotora.service_ficha.model.FichaVehiculo;
import com.automotora.service_ficha.repository.FichaVehiculoRepository;

@Service
public class FichaVehiculoService {

    @Autowired
    private FichaVehiculoRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public FichaVehiculoService(FichaVehiculoRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    // Crear ficha validando vehículo
    public FichaVehiculo guardarFicha(FichaVehiculo ficha) {
        if (ficha.getVehiculoId() != null) {
            Object datosVehiculo = webClientBuilder.build()
                .get()
                .uri("http://localhost:9001/vehiculos/" + ficha.getVehiculoId()) // 👈 puerto correcto
                .retrieve()
                .bodyToMono(Object.class)
                .block();

            if (datosVehiculo == null) {
                throw new RuntimeException("Vehículo no encontrado en el microservicio Vehículo");
            }
        }

        ficha.setVendida(false); // por defecto
        return repository.save(ficha);
    }

    // Listar todas
    public List<FichaVehiculo> listarFichas() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<FichaVehiculo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Buscar por vehiculoId
    public List<FichaVehiculo> buscarPorVehiculoId(Long vehiculoId) {
        return repository.findByVehiculoId(vehiculoId);
    }

    // Actualizar ficha
    public FichaVehiculo actualizarFicha(FichaVehiculo ficha) {
        return repository.save(ficha);
    }

    // Eliminar ficha
    public void eliminarFicha(Long id) {
        repository.deleteById(id);
    }

    // Ficha enriquecida con datos del vehículo
    public Map<String, Object> obtenerFichaConVehiculo(Long idFicha) {
        FichaVehiculo ficha = repository.findById(idFicha).orElse(null);
        if (ficha == null) return null;

        Object vehiculo;
        try {
            vehiculo = webClientBuilder.build()
                .get()
                .uri("http://localhost:9001/vehiculos/" + ficha.getVehiculoId()) // 👈 puerto correcto
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        } catch (Exception e) {
            vehiculo = "Información de vehículo no disponible actualmente";
        }

        return Map.of(
            "ficha", ficha,
            "vehiculo", vehiculo
        );
    }
}