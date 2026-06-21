package com.automotora.service_mantenimiento.service;

import com.automotora.service_mantenimiento.model.Mantenimiento;
import com.automotora.service_mantenimiento.repository.MantenimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final WebClient.Builder webClientBuilder;

    public MantenimientoService(MantenimientoRepository mantenimientoRepository,
                                WebClient.Builder webClientBuilder) {
        this.mantenimientoRepository = mantenimientoRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Mantenimiento> listarTodos() {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findAll();
        mantenimientos.forEach(this::enriquecerDatos);
        return mantenimientos;
    }

    public Mantenimiento buscarPorId(Long id) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));
        enriquecerDatos(mantenimiento);
        return mantenimiento;
    }

    public Mantenimiento crear(Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    public Mantenimiento actualizar(Long id, Mantenimiento mantenimiento) {
        Mantenimiento existente = buscarPorId(id);
        existente.setFecha(mantenimiento.getFecha());
        existente.setTipoServicio(mantenimiento.getTipoServicio());
        existente.setCosto(mantenimiento.getCosto());
        existente.setFichaVehiculoId(mantenimiento.getFichaVehiculoId());
        existente.setEmpleadoId(mantenimiento.getEmpleadoId());
        return mantenimientoRepository.save(existente);
    }

    public void eliminar(Long id) {
        mantenimientoRepository.deleteById(id);
    }

    // 🔗 Método para enriquecer con datos de otros microservicios
    private void enriquecerDatos(Mantenimiento mantenimiento) {
        // Llamada al microservicio FichaVehiculo
        Object ficha = webClientBuilder.build().get()
                .uri("http://localhost:9003/api/v1/fichas/" + mantenimiento.getFichaVehiculoId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        mantenimiento.setDatosFichaVehiculo(ficha);

        // Llamada al microservicio Empleado
        Object empleado = webClientBuilder.build().get()
                .uri("http://localhost:9006/api/v1/empleados/" + mantenimiento.getEmpleadoId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        mantenimiento.setDatosEmpleado(empleado);
    }
}