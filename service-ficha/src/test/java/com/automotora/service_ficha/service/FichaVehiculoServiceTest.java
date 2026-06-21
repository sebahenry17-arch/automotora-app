package com.automotora.service_ficha.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.automotora.service_ficha.dto.VehiculoDTO;
import com.automotora.service_ficha.model.FichaVehiculo;
import com.automotora.service_ficha.repository.FichaVehiculoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class FichaVehiculoServiceTest {

    @Mock
    private FichaVehiculoRepository repository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private FichaVehiculoService fichaService;

    // -------------------------------
    // guardarFicha (éxito)
    // -------------------------------
    
    @Test
    void guardarFicha_conVehiculoExistente() {
        FichaVehiculo ficha = new FichaVehiculo();
        ficha.setVehiculoId(10L);
        ficha.setNumeroSerie("ABC123");

        VehiculoDTO vehiculoMock = new VehiculoDTO(10L, "Toyota", "Corolla", 2020, 5, "Sedán");

        when(repository.save(ficha)).thenReturn(ficha);

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec<?> uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString());
        doReturn(responseSpec).when(headersSpec).retrieve();
        when(responseSpec.bodyToMono(VehiculoDTO.class)).thenReturn(Mono.just(vehiculoMock));

        FichaVehiculo resultado = fichaService.guardarFicha(ficha);

        assertNotNull(resultado);
        assertEquals("ABC123", resultado.getNumeroSerie());
        assertFalse(resultado.getVendida());
        verify(repository, times(1)).save(ficha);
    }

    // -------------------------------
    // guardarFicha (error)
    // -------------------------------
    
    @Test
    void guardarFicha_conVehiculoInexistente() {
        FichaVehiculo ficha = new FichaVehiculo();
        ficha.setVehiculoId(99L);

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec<?> uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString());
        doReturn(responseSpec).when(headersSpec).retrieve();
        when(responseSpec.bodyToMono(VehiculoDTO.class)).thenReturn(Mono.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> fichaService.guardarFicha(ficha));
        assertEquals("Vehículo no encontrado en el microservicio Vehículo", ex.getMessage());
    }

    // -------------------------------
    // listarFichas
    // -------------------------------
    @Test
    void listarFichas_deberiaRetornarLista() {
        FichaVehiculo f1 = new FichaVehiculo();
        f1.setId(1L);
        FichaVehiculo f2 = new FichaVehiculo();
        f2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<FichaVehiculo> resultado = fichaService.listarFichas();

        assertEquals(2, resultado.size());
    }

    // -------------------------------
    // buscarPorId
    // -------------------------------
    @Test
    void buscarPorId_deberiaRetornarOptional() {
        FichaVehiculo ficha = new FichaVehiculo();
        ficha.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(ficha));

        Optional<FichaVehiculo> resultado = fichaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    // -------------------------------
    // actualizarFicha
    // -------------------------------
    @Test
    void actualizarFicha_deberiaGuardarCambios() {
        FichaVehiculo ficha = new FichaVehiculo();
        ficha.setId(1L);
        ficha.setNumeroSerie("XYZ789");

        when(repository.save(ficha)).thenReturn(ficha);

        FichaVehiculo resultado = fichaService.actualizarFicha(ficha);

        assertEquals("XYZ789", resultado.getNumeroSerie());
    }

    // -------------------------------
    // eliminarFicha
    // -------------------------------
    @Test
    void eliminarFicha_deberiaInvocarRepositorio() {
        fichaService.eliminarFicha(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    // -------------------------------
    // obtenerFichaConVehiculo (éxito)
    // -------------------------------
    
    @Test
    void obtenerFichaConVehiculo_deberiaRetornarFichaConDTO() {
        FichaVehiculo ficha = new FichaVehiculo();
        ficha.setId(1L);
        ficha.setVehiculoId(10L);

        VehiculoDTO vehiculoMock = new VehiculoDTO(10L, "Toyota", "Corolla", 2020, 5, "Sedán");

        when(repository.findById(1L)).thenReturn(Optional.of(ficha));

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec<?> uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString());
        doReturn(responseSpec).when(headersSpec).retrieve();
        when(responseSpec.bodyToMono(VehiculoDTO.class)).thenReturn(Mono.just(vehiculoMock));

        FichaVehiculo resultado = fichaService.obtenerFichaConVehiculo(1L);

        assertNotNull(resultado.getDatosVehiculo());
        assertEquals("Toyota", resultado.getDatosVehiculo().getMarca());
        verify(repository, times(1)).findById(1L);
    }

    // -------------------------------
    // obtenerFichaConVehiculo (error)
    // -------------------------------
    
    @Test
    void obtenerFichaConVehiculo_errorEnWebClient() {
        FichaVehiculo ficha = new FichaVehiculo();
        ficha.setId(1L);
        ficha.setVehiculoId(10L);

        when(repository.findById(1L)).thenReturn(Optional.of(ficha));

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec<?> uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString());
        doThrow(new RuntimeException("Error de conexión")).when(headersSpec).retrieve();

        FichaVehiculo resultado = fichaService.obtenerFichaConVehiculo(1L);

        assertNotNull(resultado);
        assertNull(resultado.getDatosVehiculo());
    }
}