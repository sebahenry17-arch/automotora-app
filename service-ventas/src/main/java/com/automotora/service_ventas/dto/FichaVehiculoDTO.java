package com.automotora.service_ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos simplificados de la ficha del vehículo para pruebas y respuestas")
public class FichaVehiculoDTO {

    @Schema(description = "Identificador único de la ficha", example = "2")
    private Long id;

    @Schema(description = "Número de serie del vehículo", example = "ABC123XYZ")
    private String numeroSerie;

    @Schema(description = "Color del vehículo", example = "Rojo")
    private String color;

    @Schema(description = "Tipo de combustible", example = "Gasolina")
    private String combustible;

    @Schema(description = "Tipo de transmisión", example = "Manual")
    private String transmision;

    @Schema(description = "Fecha de matrícula", example = "2020-05-15")
    private LocalDate fechaMatricula;

    @Schema(description = "Precio del vehículo", example = "15000.0")
    private Double precio;

    @Schema(description = "Estado de venta", example = "false")
    private Boolean vendida;
}