package com.automotora.service_financiamiento.service;

import com.automotora.service_financiamiento.model.Financiamiento;
import com.automotora.service_financiamiento.repository.FinanciamientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinanciamientoServiceTest {

    @InjectMocks
    private FinanciamientoService financiamientoService;

    @Mock
    private FinanciamientoRepository financiamientoRepository;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    void guardarFinanciamiento_conClienteYVentaValida_devuelveFinanciamientoGuardado() {
        Financiamiento f = new Financiamiento();
        f.setId(1L);
        f.setTipo("Crédito");
        f.setCuotas(12);
        f.setMonto(500000.0);
        f.setEstado("Activo");
        f.setClienteId(10L);
        f.setVentaId(20L);

        when(financiamientoRepository.save(any(Financiamiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Financiamiento resultado = financiamientoService.guardar(f);

        assertNotNull(resultado);
        assertEquals("Crédito", resultado.getTipo());
        assertEquals(12, resultado.getCuotas());
        assertEquals(500000.0, resultado.getMonto());
        assertEquals("Activo", resultado.getEstado());
    }

    @Test
    void actualizarFinanciamiento_conDatosValidos_devuelveFinanciamientoActualizado() {
        Financiamiento existente = new Financiamiento();
        existente.setId(1L);
        existente.setTipo("Crédito");
        existente.setCuotas(12);
        existente.setMonto(500000.0);
        existente.setEstado("Activo");
        existente.setClienteId(10L);
        existente.setVentaId(20L);

        Financiamiento nuevo = new Financiamiento();
        nuevo.setTipo("Leasing");
        nuevo.setCuotas(24);
        nuevo.setMonto(800000.0);
        nuevo.setEstado("Pendiente");
        nuevo.setClienteId(10L);
        nuevo.setVentaId(20L);

        when(financiamientoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(financiamientoRepository.save(any(Financiamiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Financiamiento resultado = financiamientoService.actualizar(1L, nuevo);

        assertNotNull(resultado);
        assertEquals("Leasing", resultado.getTipo());
        assertEquals(24, resultado.getCuotas());
        assertEquals(800000.0, resultado.getMonto());
        assertEquals("Pendiente", resultado.getEstado());
    }
}
