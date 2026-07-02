package com.automotora.service_empleado.service;

import com.automotora.service_empleado.model.Empleado;
import com.automotora.service_empleado.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void listarTodos_deberiaRetornarLista() {
        Empleado e1 = new Empleado();
        e1.setId(1L);
        e1.setNombre("Juan");
        Empleado e2 = new Empleado();
        e2.setId(2L);
        e2.setNombre("Maria");

        when(empleadoRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Empleado> resultado = empleadoService.listarTodos();

        assertEquals(2, resultado.size());
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_deberiaRetornarOptional() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Juan");

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        Optional<Empleado> resultado = empleadoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("Juan", resultado.get().getNombre());
        verify(empleadoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_noExistente_deberiaRetornarOptionalVacio() {
        when(empleadoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Empleado> resultado = empleadoService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
        verify(empleadoRepository, times(1)).findById(99L);
    }

    @Test
    void guardar_deberiaGuardarEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Juan");
        empleado.setCargo("Vendedor");

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

        Empleado resultado = empleadoService.guardar(empleado);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Vendedor", resultado.getCargo());
        verify(empleadoRepository, times(1)).save(empleado);
    }

    @Test
    void actualizar_conIdExistente_deberiaActualizarEmpleado() {
        Empleado existente = new Empleado();
        existente.setId(1L);
        existente.setNombre("Juan");
        existente.setCargo("Vendedor");

        Empleado nuevo = new Empleado();
        nuevo.setNombre("Juan Actualizado");
        nuevo.setCargo("Gerente");
        nuevo.setTelefono("123456789");
        nuevo.setEmail("juan@test.com");

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Empleado resultado = empleadoService.actualizar(1L, nuevo);

        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals("Gerente", resultado.getCargo());
        assertEquals("123456789", resultado.getTelefono());
        assertEquals("juan@test.com", resultado.getEmail());
        verify(empleadoRepository, times(1)).findById(1L);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    void actualizar_conIdNoExistente_deberiaLanzarExcepcion() {
        Empleado nuevo = new Empleado();
        nuevo.setNombre("Juan");

        when(empleadoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> empleadoService.actualizar(99L, nuevo));

        assertEquals("Empleado con ID 99 no existe", ex.getMessage());
        verify(empleadoRepository, times(1)).findById(99L);
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @Test
    void eliminar_deberiaInvocarRepositorio() {
        empleadoService.eliminar(1L);

        verify(empleadoRepository, times(1)).deleteById(1L);
    }
}
