package com.automotora.service_ficha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.automotora.service_ficha.model.FichaVehiculo;
import com.automotora.service_ficha.service.FichaVehiculoService;

@RestController
@RequestMapping("/api/v1/fichas")
@CrossOrigin(origins = "*")
@Tag(name = "Fichas de Vehículos", description = "Endpoint para la gestión de fichas de vehículos con HATEOAS")
public class FichaVehiculoController {

    @Autowired
    private FichaVehiculoService fichaService;

    @PostMapping
    @Operation(summary = "Crear una nueva ficha de vehículo")
    public FichaVehiculo crear(@RequestBody FichaVehiculo ficha) {
        return fichaService.guardarFicha(ficha);
    }

    @GetMapping
    @Operation(summary = "Listar todas las fichas de vehículos")
    public List<FichaVehiculo> listar() {
        return fichaService.listarFichas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ficha con link al microservicio de vehículos")
    public EntityModel<FichaVehiculo> obtenerUna(@PathVariable Long id) {
        // 1. Buscamos la ficha
        FichaVehiculo ficha = fichaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Ficha no encontrada con ID: " + id));

        // 2. Creamos el recurso HATEOAS
        EntityModel<FichaVehiculo> recurso = EntityModel.of(ficha);

        // 3. Link a sí mismo
        recurso.add(linkTo(methodOn(FichaVehiculoController.class).obtenerUna(id)).withSelfRel());

        // 4. Link dinámico al microservicio de Vehículos (vía API Gateway en puerto 9090)
        String urlVehiculo = "http://localhost:9090/api/v1/vehiculos/" + ficha.getVehiculoId();
        Link linkVehiculo = Link.of(urlVehiculo, "detalle-vehiculo");
        recurso.add(linkVehiculo);

        return recurso;
    }

     @Operation(
        summary = "Actualizar ficha de vehículo",
        description = "Actualiza una ficha existente en la base de datos usando su ID y los nuevos datos enviados en el cuerpo de la petición."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ficha actualizada correctamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = FichaVehiculo.class))),
        @ApiResponse(responseCode = "404", description = "Ficha no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FichaVehiculo> actualizarFicha(
            @PathVariable Long id,
            @RequestBody FichaVehiculo ficha) {

        ficha.setId(id); // aseguramos que se actualice la ficha correcta
        FichaVehiculo fichaActualizada = fichaService.actualizarFicha(ficha);

        return ResponseEntity.ok(fichaActualizada);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ficha de vehículo")
    public void eliminar(@PathVariable Long id) {
        fichaService.eliminarFicha(id);
    }
}