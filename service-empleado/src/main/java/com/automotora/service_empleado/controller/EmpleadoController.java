
package com.automotora.service_empleado.controller;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automotora.service_empleado.model.Empleado;
import com.automotora.service_empleado.service.EmpleadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.automotora.service_empleado.config.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @Operation(summary = "Listar todos los empleados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Empleado>>> listarTodos() {
        List<EntityModel<Empleado>> empleados = empleadoService.listarTodos().stream()
                .map(e -> EntityModel.of(e,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpleadoController.class).buscarPorId(e.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpleadoController.class).listarTodos()).withRel("todos-los-empleados"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpleadoController.class).eliminar(e.getId())).withRel("eliminar")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(empleados));
    }

    @Operation(summary = "Buscar empleado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Empleado>> buscarPorId(@PathVariable Long id) {
        Empleado empleado = empleadoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado con ID " + id + " no existe"));

        EntityModel<Empleado> resource = EntityModel.of(empleado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpleadoController.class).buscarPorId(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpleadoController.class).listarTodos()).withRel("todos-los-empleados"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpleadoController.class).eliminar(id)).withRel("eliminar")
        );

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Crear un nuevo empleado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Empleado> guardar(@RequestBody Empleado empleado) {
        return ResponseEntity.status(201).body(empleadoService.guardar(empleado));
    }

    @Operation(summary = "Actualizar un empleado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        return ResponseEntity.ok(empleadoService.actualizar(id, empleado));
    }

    @Operation(summary = "Eliminar un empleado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empleado eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
