package com.automotora.service_ventas.service;



import com.automotora.service_ventas.model.Venta;

import com.automotora.service_ventas.repository.VentaRepository;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.*;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;



import java.time.LocalDate;

import java.util.Optional;



import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;



class VentaServiceTest {



    @InjectMocks

    private VentaService ventaService;



    @Mock

    private VentaRepository ventaRepository;



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

    void guardarVenta_conClienteYFichaValida_devuelveVentaGuardada() {

        Venta venta = new Venta();

        venta.setId(1L);

        venta.setFecha(LocalDate.now());

        venta.setMonto(1000.0);

        venta.setClienteId(10L);

        venta.setFichaId(20L);

        // Mock Cliente and Ficha (same mock chain used twice)
        doReturn(requestHeadersUriSpec).doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just("ClienteMock")).doReturn(Mono.just("FichaMock")).when(responseSpec).bodyToMono(Object.class);

        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));



        Venta resultado = ventaService.guardarVenta(venta);



        assertNotNull(resultado);

        assertEquals("ClienteMock", resultado.getDatosCliente());

        assertEquals("FichaMock", resultado.getDatosFichaVehiculo());

    }



    @Test

    void actualizarVenta_conDatosValidos_devuelveVentaActualizada() {

        Venta existente = new Venta();

        existente.setId(1L);

        existente.setFecha(LocalDate.now());

        existente.setMonto(500.0);

        existente.setClienteId(10L);

        existente.setFichaId(20L);



        Venta nueva = new Venta();

        nueva.setFecha(LocalDate.now());

        nueva.setMonto(1500.0);

        nueva.setClienteId(10L);

        nueva.setFichaId(20L);



        when(ventaRepository.findById(1L)).thenReturn(Optional.of(existente));

        // Mock Cliente and Ficha (same mock chain used twice)
        doReturn(requestHeadersUriSpec).doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just("ClienteMock")).doReturn(Mono.just("FichaMock")).when(responseSpec).bodyToMono(Object.class);

        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));



        Venta resultado = ventaService.actualizarVenta(1L, nueva);



        assertNotNull(resultado);

        assertEquals(1500.0, resultado.getMonto());

        assertEquals("ClienteMock", resultado.getDatosCliente());

        assertEquals("FichaMock", resultado.getDatosFichaVehiculo());

    }
}