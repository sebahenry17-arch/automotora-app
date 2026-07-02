package com.automotora.service_inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automotora.service_inventario.config.ErrorResponse;
import com.automotora.service_inventario.model.Repuesto;
import com.automotora.service_inventario.service.RepuestoService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/inventarios")
@CrossOrigin(origins = "*")
@Tag(name = "Inventario", description = "Endpoint para la gestión de repuestos con HATEOAS")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo repuesto")
    public Repuesto crear(@RequestBody Repuesto repuesto) {
        return repuestoService.guardarRepuesto(repuesto);
    }

    @GetMapping
    @Operation(summary = "Listar todos los repuestos")
    public List<Repuesto> listar() {
        return repuestoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener repuesto con link dinámico a Proveedor",
        description = "Devuelve un repuesto enriquecido con datos del Proveedor"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Repuesto encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Repuesto.class))),
        @ApiResponse(responseCode = "404", description = "Repuesto no encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    public EntityModel<Repuesto> obtenerUno(@PathVariable Long id) {
        Repuesto repuesto = repuestoService.buscarPorId(id);

        EntityModel<Repuesto> recurso = EntityModel.of(repuesto);

        // Link a sí mismo
        recurso.add(linkTo(methodOn(RepuestoController.class).obtenerUno(id)).withSelfRel());

        // Link dinámico al microservicio Proveedor (puerto 9010)
        String urlProveedor = "http://localhost:9010/api/v1/proveedores/" + repuesto.getProveedorId();
        recurso.add(Link.of(urlProveedor, "proveedor"));

        return recurso;
    }

    @Operation(
        summary = "Actualizar repuesto",
        description = "Actualiza un repuesto existente en el inventario usando su ID y los nuevos datos enviados en el cuerpo de la petición."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Repuesto actualizado correctamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Repuesto.class))),
        @ApiResponse(responseCode = "404", description = "Repuesto no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> actualizarRepuesto(
            @PathVariable Long id,
            @RequestBody Repuesto repuesto) {

        Repuesto actualizado = repuestoService.actualizarRepuesto(id, repuesto);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un repuesto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            repuestoService.eliminarRepuesto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}