package com.automotora.service_auth.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String nombreUsuario;
    private String contraseña;
    private String correo;
    private String telefono;
    private String rut;

    private Set<String> roles;
}
