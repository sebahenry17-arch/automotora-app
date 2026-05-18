package com.automotora.service_financiamiento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Financiamiento {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;       
    private Integer cuotas;    
    private Double monto;      
    private String estado;

    private Long clienteId;
    private Long ventaId;

    
    @Transient
    private Object datosCliente;

    @Transient
    private Object datosVenta;
}
