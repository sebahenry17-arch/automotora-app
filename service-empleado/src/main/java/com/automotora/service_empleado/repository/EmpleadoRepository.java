package com.automotora.service_empleado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automotora.service_empleado.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

  
}
