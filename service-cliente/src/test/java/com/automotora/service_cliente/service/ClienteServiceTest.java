package com.automotora.service_cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.automotora.service_cliente.model.Cliente;
import com.automotora.service_cliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void obtenerClientePorId_existente_devuelveCliente() {
        // --- 1. PREPARACIÓN ---
        Long clienteId = 1L;
        Cliente clienteMock = new Cliente();
        clienteMock.setId(clienteId);
        clienteMock.setNombre("Juan");
        clienteMock.setRut("12345678-9");
        clienteMock.setTelefono("987654321");
        clienteMock.setEmail("juan@mail.com");
        clienteMock.setHistorialCompras("Compra1");

        // Mock del repositorio
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

        // --- 2. EJECUCIÓN ---
        Cliente resultado = clienteService.obtenerClientePorId(clienteId);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El cliente no debe ser nulo");
        assertEquals("Juan", resultado.getNombre(), "El nombre debe coincidir con el simulado");
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("juan@mail.com", resultado.getEmail());
        assertEquals("Compra1", resultado.getHistorialCompras());
        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void obtenerClientePorId_inexistente_lanzaExcepcion() {
        // --- 1. PREPARACIÓN ---
        Long clienteId = 99L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // --- 2. EJECUCIÓN + VERIFICACIÓN ---
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.obtenerClientePorId(clienteId));

        assertEquals("Cliente con ID 99 no existe", ex.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
    }
}