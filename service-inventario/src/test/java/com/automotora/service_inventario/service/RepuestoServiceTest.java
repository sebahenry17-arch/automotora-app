package com.automotora.service_inventario.service;

import com.automotora.service_inventario.model.Repuesto;
import com.automotora.service_inventario.repository.RepuestoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RepuestoServiceTest {

    @Mock
    private RepuestoRepository repuestoRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private RepuestoService repuestoService;

    @BeforeEach
    void setUp() {
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    void guardarRepuesto_conProveedorValido_devuelveRepuestoGuardado() {
        Repuesto repuesto = new Repuesto();
        repuesto.setId(1L);
        repuesto.setNombre("Filtro de aceite");
        repuesto.setCantidadDisponible(10);
        repuesto.setProveedorId(10L);

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just("ProveedorMock")).when(responseSpec).bodyToMono(Object.class);

        when(repuestoRepository.save(any(Repuesto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Repuesto resultado = repuestoService.guardarRepuesto(repuesto);

        assertNotNull(resultado);
        assertEquals("Filtro de aceite", resultado.getNombre());
        assertEquals("ProveedorMock", resultado.getDatosProveedor());
        verify(repuestoRepository, times(1)).save(repuesto);
    }

    @Test
    void guardarRepuesto_conProveedorInexistente_lanzaExcepcion() {
        Repuesto repuesto = new Repuesto();
        repuesto.setProveedorId(99L);

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doThrow(new RuntimeException("Error")).when(requestHeadersSpec).retrieve();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repuestoService.guardarRepuesto(repuesto));

        assertEquals("Proveedor no existe con ID: 99", ex.getMessage());
    }

    @Test
    void listarTodos_deberiaRetornarLista() {
        Repuesto r1 = new Repuesto();
        r1.setId(1L);
        Repuesto r2 = new Repuesto();
        r2.setId(2L);

        when(repuestoRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Repuesto> resultado = repuestoService.listarTodos();

        assertEquals(2, resultado.size());
        verify(repuestoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_conIdExistente_deberiaRetornarRepuesto() {
        Repuesto repuesto = new Repuesto();
        repuesto.setId(1L);
        repuesto.setNombre("Filtro de aceite");

        when(repuestoRepository.findById(1L)).thenReturn(Optional.of(repuesto));

        Repuesto resultado = repuestoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repuestoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_conIdNoExistente_deberiaLanzarExcepcion() {
        when(repuestoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repuestoService.buscarPorId(99L));

        assertEquals("Repuesto no encontrado con ID: 99", ex.getMessage());
        verify(repuestoRepository, times(1)).findById(99L);
    }

    @Test
    void actualizarRepuesto_conIdExistente_deberiaActualizarRepuesto() {
        Repuesto existente = new Repuesto();
        existente.setId(1L);
        existente.setNombre("Filtro viejo");
        existente.setCantidadDisponible(5);

        Repuesto nuevo = new Repuesto();
        nuevo.setNombre("Filtro nuevo");
        nuevo.setCantidadDisponible(20);
        nuevo.setProveedorId(10L);

        when(repuestoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(repuestoRepository.save(any(Repuesto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just("ProveedorMock")).when(responseSpec).bodyToMono(Object.class);

        Repuesto resultado = repuestoService.actualizarRepuesto(1L, nuevo);

        assertNotNull(resultado);
        assertEquals("Filtro nuevo", resultado.getNombre());
        assertEquals(20, resultado.getCantidadDisponible());
        assertEquals("ProveedorMock", resultado.getDatosProveedor());
        verify(repuestoRepository, times(1)).findById(1L);
        verify(repuestoRepository, times(1)).save(any(Repuesto.class));
    }

    @Test
    void actualizarRepuesto_conIdNoExistente_deberiaLanzarExcepcion() {
        Repuesto nuevo = new Repuesto();
        nuevo.setNombre("Filtro");

        when(repuestoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repuestoService.actualizarRepuesto(99L, nuevo));

        assertEquals("Repuesto no encontrado con ID: 99", ex.getMessage());
        verify(repuestoRepository, times(1)).findById(99L);
        verify(repuestoRepository, never()).save(any(Repuesto.class));
    }

    @Test
    void eliminarRepuesto_conIdExistente_deberiaEliminar() {
        when(repuestoRepository.existsById(1L)).thenReturn(true);

        repuestoService.eliminarRepuesto(1L);

        verify(repuestoRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarRepuesto_conIdNoExistente_deberiaLanzarExcepcion() {
        when(repuestoRepository.existsById(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repuestoService.eliminarRepuesto(99L));

        assertEquals("Repuesto no encontrado con ID: 99", ex.getMessage());
        verify(repuestoRepository, never()).deleteById(anyLong());
    }
}
