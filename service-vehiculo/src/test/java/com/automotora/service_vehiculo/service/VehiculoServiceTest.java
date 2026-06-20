package com.automotora.service_vehiculo.service;


import com.automotora.service_vehiculo.model.Vehiculo;
import com.automotora.service_vehiculo.repository.VehiculoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    @DisplayName("Debería guardar un vehículo correctamente")
    void guardarVehiculoTest() {
        Vehiculo v = new Vehiculo(null, "Toyota", "Corolla", 2020, 5, null);

        when(vehiculoRepository.save(any(Vehiculo.class)))
                .thenAnswer(invocation -> {
                    Vehiculo vehiculo = invocation.getArgument(0);
                    vehiculo.setId(1L);
                    return vehiculo;
                });

        Vehiculo resultado = vehiculoService.crearVehiculo(v);

        assertNotNull(resultado.getId());
        assertEquals("Toyota", resultado.getMarca());
        verify(vehiculoRepository, times(1)).save(v);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la marca está vacía")
    void guardarVehiculoMarcaVaciaTest() {
        Vehiculo v = new Vehiculo(null, "", "Corolla", 2020, 5, null);

        assertThrows(IllegalArgumentException.class,
                () -> vehiculoService.crearVehiculo(v));
    }

    @Test
    @DisplayName("Debería lanzar excepción si el año es inválido")
    void guardarVehiculoAnioInvalidoTest() {
        Vehiculo v = new Vehiculo(null, "Toyota", "Corolla", 1800, 5, null);

        assertThrows(IllegalArgumentException.class,
                () -> vehiculoService.crearVehiculo(v));
    }

    @Test
    @DisplayName("Debería buscar vehículo por ID")
    void buscarVehiculoPorIdTest() {
        Vehiculo v = new Vehiculo(1L, "Honda", "Civic", 2021, 3, null);
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(v));

        Optional<Vehiculo> resultado = vehiculoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Honda", resultado.get().getMarca());
        verify(vehiculoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería eliminar vehículo por ID")
    void eliminarVehiculoTest() {
        doNothing().when(vehiculoRepository).deleteById(1L);

        vehiculoService.eliminar(1L);

        verify(vehiculoRepository, times(1)).deleteById(1L);
    }
}
