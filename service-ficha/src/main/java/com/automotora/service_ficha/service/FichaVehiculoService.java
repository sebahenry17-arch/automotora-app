package com.automotora.service_ficha.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automotora.service_ficha.dto.VehiculoDTO;
import com.automotora.service_ficha.model.FichaVehiculo;
import com.automotora.service_ficha.repository.FichaVehiculoRepository;

@Service
public class FichaVehiculoService {

    @Autowired
    private FichaVehiculoRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Crear ficha validando vehículo
    public FichaVehiculo guardarFicha(FichaVehiculo ficha) {
        if (ficha.getVehiculoId() != null) {
            VehiculoDTO datosVehiculo = webClientBuilder.build()
                .get()
                .uri("http://localhost:9001/vehiculos/" + ficha.getVehiculoId())
                .retrieve()
                .bodyToMono(VehiculoDTO.class)
                .block();

            if (datosVehiculo == null) {
                throw new RuntimeException("Vehículo no encontrado en el microservicio Vehículo");
            }

            ficha.setDatosVehiculo(datosVehiculo);
        }

        ficha.setVendida(false); // por defecto
        return repository.save(ficha);
    }

    // Listar todas las fichas
    public List<FichaVehiculo> listarFichas() {
        return repository.findAll();
    }

    // Buscar ficha por ID
    public Optional<FichaVehiculo> buscarPorId(Long id) {
        return repository.findById(id)
            .map(this::enriquecerConVehiculo);
    }

    // Buscar fichas por vehiculoId
    public List<FichaVehiculo> buscarPorVehiculoId(Long vehiculoId) {
        List<FichaVehiculo> fichas = repository.findByVehiculoId(vehiculoId);
        fichas.forEach(this::enriquecerConVehiculo);
        return fichas;
    }

    // Actualizar ficha
    public FichaVehiculo actualizarFicha(FichaVehiculo ficha) {
        return repository.save(ficha);
    }

    // Eliminar ficha
    public void eliminarFicha(Long id) {
        repository.deleteById(id);
    }

    // Obtener ficha enriquecida con datos del vehículo
    public FichaVehiculo obtenerFichaConVehiculo(Long idFicha) {
        FichaVehiculo ficha = repository.findById(idFicha)
            .orElseThrow(() -> new RuntimeException("Ficha no encontrada"));
        return enriquecerConVehiculo(ficha);
    }

    // Método privado para enriquecer con datos del microservicio Vehículo
    private FichaVehiculo enriquecerConVehiculo(FichaVehiculo ficha) {
        if (ficha.getVehiculoId() != null) {
            try {
                VehiculoDTO datosVehiculo = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:9001/vehiculos/" + ficha.getVehiculoId())
                    .retrieve()
                    .bodyToMono(VehiculoDTO.class)
                    .block();

                ficha.setDatosVehiculo(datosVehiculo);
            } catch (Exception e) {
                ficha.setDatosVehiculo(null); // si falla la llamada, no rompe el flujo
            }
        }
        return ficha;
    }
}