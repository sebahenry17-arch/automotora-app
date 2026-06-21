package com.automotora.service_auth.service;

import com.automotora.service_auth.dto.AuthRequest;
import com.automotora.service_auth.model.Cliente;
import com.automotora.service_auth.model.Rol;
import com.automotora.service_auth.repository.ClienteRepository;
import com.automotora.service_auth.repository.RolRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired 
    private RolRepository rolRepository;
    
    @Value("${jwt.secret}")
    private String secreto;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registro de cliente
    public String registrar(AuthRequest request) {
        if (clienteRepository.findByNombreUsuario(request.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe.");
        }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombreUsuario(request.getNombreUsuario());
        nuevoCliente.setCorreo(request.getCorreo());
        nuevoCliente.setContraseña(passwordEncoder.encode(request.getContraseña()));
        nuevoCliente.setTelefono(request.getTelefono());
        nuevoCliente.setRut(request.getRut());

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            Rol rolPorDefecto = rolRepository.findByNombre("CLIENTE")
                    .orElseThrow(() -> new RuntimeException("Error: El rol CLIENTE no existe en la DB."));
            nuevoCliente.getRoles().add(rolPorDefecto);
        } else {
            for (String nombreRol : request.getRoles()) {
                Rol rolEncontrado = rolRepository.findByNombre(nombreRol.toUpperCase())
                        .orElseThrow(() -> new RuntimeException("Error: El rol " + nombreRol + " no existe en la DB."));
                nuevoCliente.getRoles().add(rolEncontrado);
            }
        }

        clienteRepository.save(nuevoCliente);
        return "Cliente registrado correctamente";
    }

    @Transactional(readOnly = true)
    public String login(String nombreUsuario, String contraseña) {
    
        Cliente cliente = clienteRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(contraseña, cliente.getContraseña())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        List<String> rolesList = cliente.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());

        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + 86400000); 

        return Jwts.builder()
                .setSubject(cliente.getNombreUsuario())
                .claim("roles", rolesList)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256) 
                .compact();
    }
}
