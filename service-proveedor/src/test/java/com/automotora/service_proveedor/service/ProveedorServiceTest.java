package com.automotora.service_proveedor.service;

import com.automotora.service_proveedor.model.Proveedor;
import com.automotora.service_proveedor.repository.ProveedorRepository;
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
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void listar_deberiaRetornarLista() {
        Proveedor p1 = new Proveedor();
        p1.setId(1L);
        p1.setNombre("Proveedor A");
        Proveedor p2 = new Proveedor();
        p2.setId(2L);
        p2.setNombre("Proveedor B");

        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Proveedor> resultado = proveedorService.listar();

        assertEquals(2, resultado.size());
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    void detalle_conIdExistente_deberiaRetornarProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor A");

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        Proveedor resultado = proveedorService.detalle(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Proveedor A", resultado.getNombre());
        verify(proveedorRepository, times(1)).findById(1L);
    }

    @Test
    void detalle_conIdNoExistente_deberiaLanzarExcepcion() {
        when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> proveedorService.detalle(99L));

        assertEquals("Proveedor no encontrado", ex.getMessage());
        verify(proveedorRepository, times(1)).findById(99L);
    }

    @Test
    void crear_deberiaGuardarProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor A");
        proveedor.setRutDni("12345678-9");

        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        Proveedor resultado = proveedorService.crear(proveedor);

        assertNotNull(resultado);
        assertEquals("Proveedor A", resultado.getNombre());
        assertEquals("12345678-9", resultado.getRutDni());
        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    void actualizar_conIdExistente_deberiaActualizarProveedor() {
        Proveedor existente = new Proveedor();
        existente.setId(1L);
        existente.setNombre("Proveedor A");
        existente.setRutDni("12345678-9");

        Proveedor nuevo = new Proveedor();
        nuevo.setNombre("Proveedor Actualizado");
        nuevo.setRutDni("98765432-1");
        nuevo.setTelefono("123456789");
        nuevo.setEmail("proveedor@test.com");
        nuevo.setDireccion("Calle 123");

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(proveedorRepository.save(any(Proveedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Proveedor resultado = proveedorService.actualizar(1L, nuevo);

        assertNotNull(resultado);
        assertEquals("Proveedor Actualizado", resultado.getNombre());
        assertEquals("98765432-1", resultado.getRutDni());
        assertEquals("123456789", resultado.getTelefono());
        assertEquals("proveedor@test.com", resultado.getEmail());
        assertEquals("Calle 123", resultado.getDireccion());
        verify(proveedorRepository, times(1)).findById(1L);
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    void actualizar_conIdNoExistente_deberiaLanzarExcepcion() {
        Proveedor nuevo = new Proveedor();
        nuevo.setNombre("Proveedor A");

        when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> proveedorService.actualizar(99L, nuevo));

        assertEquals("Proveedor no encontrado", ex.getMessage());
        verify(proveedorRepository, times(1)).findById(99L);
        verify(proveedorRepository, never()).save(any(Proveedor.class));
    }

    @Test
    void eliminar_deberiaInvocarRepositorio() {
        proveedorService.eliminar(1L);

        verify(proveedorRepository, times(1)).deleteById(1L);
    }
}
