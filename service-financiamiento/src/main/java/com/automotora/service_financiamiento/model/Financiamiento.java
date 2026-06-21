package com.automotora.service_financiamiento.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad Financiamiento")
public class Financiamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del financiamiento", example = "1")
    private Long id;

    @Schema(description = "Tipo de financiamiento (Crédito, Leasing, etc.)", example = "Crédito")
    private String tipo;

    @Schema(description = "Número de cuotas del financiamiento", example = "12")
    private Integer cuotas;

    @Schema(description = "Monto total financiado", example = "1500000")
    private Double monto;

    @Schema(description = "Estado del financiamiento (Activo, Pagado, Pendiente)", example = "Activo")
    private String estado;

    @Schema(description = "ID del cliente asociado al financiamiento", example = "5")
    private Long clienteId;

    @Schema(description = "ID de la venta asociada al financiamiento", example = "10")
    private Long ventaId;

    // Datos enriquecidos en tiempo de ejecución
    @Transient
    @Schema(description = "Datos completos de la venta, incluyendo cliente y ficha", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosVenta;

    @Transient
    @Schema(description = "Datos adicionales del cliente (si se requieren más detalles)", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosCliente;
}
