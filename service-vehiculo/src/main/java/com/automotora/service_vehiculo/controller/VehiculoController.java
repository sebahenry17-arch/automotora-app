package com.automotora.service_vehiculo.controller;

import com.automotora.service_vehiculo.model.Vehiculo;
import com.automotora.service_vehiculo.service.VehiculoService;
import com.automotora.service_vehiculo.config.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/vehiculo")
@CrossOrigin(origins = "*")
@Tag(name = "Vehículos", description = "Operaciones relacionadas con la gestión de vehículos con soporte HATEOAS")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Operation(summary = "Listar todos los vehículos", description = "Devuelve una colección de vehículos con enlaces")
    @GetMapping
    public CollectionModel<EntityModel<Vehiculo>> listar() {
        List<EntityModel<Vehiculo>> vehiculos = vehiculoService.listarVehiculos().stream()
                .map(vehiculo -> EntityModel.of(vehiculo,
                        linkTo(methodOn(VehiculoController.class).obtener(vehiculo.getId())).withSelfRel(),
                        linkTo(methodOn(VehiculoController.class).listar()).withRel("vehiculos")))
                .collect(Collectors.toList());

        return CollectionModel.of(vehiculos,
                linkTo(methodOn(VehiculoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener vehículo por ID", description = "Devuelve un vehículo con enlaces a acciones relacionadas")
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Vehículo encontrado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Vehiculo.class))
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Vehículo no encontrado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class))
    )
})
@GetMapping("/{id}")
public ResponseEntity<?> obtener(@PathVariable Long id) {
    var vehiculoOpt = vehiculoService.buscarPorId(id);

    if (vehiculoOpt.isPresent()) {
        Vehiculo vehiculo = vehiculoOpt.get();
        EntityModel<Vehiculo> recurso = EntityModel.of(vehiculo);
        recurso.add(linkTo(methodOn(VehiculoController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(VehiculoController.class).listar()).withRel("todos-los-vehiculos"));
        recurso.add(linkTo(methodOn(VehiculoController.class).eliminar(id)).withRel("eliminar"));
        return ResponseEntity.ok(recurso);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        404,
                        "Vehículo con ID " + id + " no existe",
                        "/api/v1/vehiculo/" + id,
                        LocalDateTime.now().toString()
                )
        );
    }
}


    @Operation(summary = "Crear vehículo", description = "Crea un nuevo vehículo y devuelve enlaces")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehículo creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vehiculo.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error de validación",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevo = vehiculoService.crearVehiculo(vehiculo);
            EntityModel<Vehiculo> recurso = EntityModel.of(nuevo,
                    linkTo(methodOn(VehiculoController.class).obtener(nuevo.getId())).withSelfRel());
            return ResponseEntity.ok(recurso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(400, e.getMessage(),
                            "/api/v1/vehiculo",
                            LocalDateTime.now().toString())
            );
        }
    }

    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Vehículo no encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            vehiculoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(404, e.getMessage(),
                            "/api/v1/vehiculo/" + id,
                            LocalDateTime.now().toString())
            );
        }
    }

    @Operation(summary = "Conteo por tipo de vehículo", description = "Devuelve el número de vehículos agrupados por tipo")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Conteo generado exitosamente"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping("/conteo")
    public ResponseEntity<?> conteoPorTipoVehiculo() {
        try {
            return ResponseEntity.ok(vehiculoService.conteoPorTipoVehiculo());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse(500, e.getMessage(),
                            "/api/v1/vehiculo/conteo",
                            LocalDateTime.now().toString())
            );
        }
    }
    @Operation(
        summary = "Actualizar vehículo",
        description = "Actualiza un vehículo existente en la base de datos usando su ID y los nuevos datos enviados en el cuerpo de la petición."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vehiculo.class))),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(
            @PathVariable Long id,
            @RequestBody Vehiculo vehiculo) {

        Vehiculo actualizado = vehiculoService.actualizarVehiculo(id, vehiculo);
        return ResponseEntity.ok(actualizado);
    }

}