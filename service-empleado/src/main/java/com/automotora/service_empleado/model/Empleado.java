package com.automotora.service_empleado.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Email(message = "Debe ser un email válido")
    private String email;
}