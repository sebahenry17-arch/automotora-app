package com.automotora.service_empleado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.service_empleado.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

  
}
