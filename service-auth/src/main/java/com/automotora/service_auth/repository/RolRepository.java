package com.automotora.service_auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.automotora.service_auth.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}