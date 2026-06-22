package com.automotora.service_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.automotora.service_auth.dto.AuthRequest;
import com.automotora.service_auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para registro y login de clientes")
public class AutenticacionController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Registrar un nuevo cliente", description = "Guarda el cliente mapeando sus roles desde el DTO")
    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @Operation(summary = "Iniciar sesión", description = "Retorna el Token JWT si las credenciales son válidas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        try {
            String token = authService.login(request.getNombreUsuario(), request.getContraseña());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}