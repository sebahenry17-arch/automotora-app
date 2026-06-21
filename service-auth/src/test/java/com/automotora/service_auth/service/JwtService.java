package com.automotora.service_auth.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secreto;

    public String generarToken(String nombreUsuario, List<String> roles) {
        // Duración del token: 2 horas
        long dosHorasEnMilisegundos = 1000L * 60 * 60 * 2;

        return Jwts.builder()
                .setSubject(nombreUsuario) // el usuario autenticado
                .claim("roles", roles)     // lista de roles del cliente
                .setIssuedAt(new Date())   // fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + dosHorasEnMilisegundos)) // fecha de expiración
                .signWith(Keys.hmacShaKeyFor(secreto.getBytes()), SignatureAlgorithm.HS256)   // firma con clave secreta
                .compact();
    }
}
