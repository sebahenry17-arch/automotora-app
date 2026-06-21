package com.automotora.service_mantenimiento.controller;

import com.automotora.service_mantenimiento.model.Mantenimiento;
import com.automotora.service_mantenimiento.service.MantenimientoService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mantenimientos")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    public MantenimientoController(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @Operation(summary = "Listar todos los mantenimientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Mantenimiento>>> listarTodos() {
        List<EntityModel<Mantenimiento>> mantenimientos = mantenimientoService.listarTodos().stream()
                .map(m -> EntityModel.of(m,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).buscarPorId(m.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).listarTodos()).withRel("todos-los-mantenimientos"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).eliminar(m.getId())).withRel("eliminar")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(mantenimientos));
    }

    @Operation(summary = "Buscar mantenimiento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mantenimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Mantenimiento no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Mantenimiento>> buscarPorId(@PathVariable Long id) {
        Mantenimiento mantenimiento = mantenimientoService.buscarPorId(id);
        EntityModel<Mantenimiento> recurso = EntityModel.of(mantenimiento,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).buscarPorId(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).listarTodos()).withRel("todos-los-mantenimientos"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).eliminar(id)).withRel("eliminar")
        );
        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Crear un mantenimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mantenimiento creado correctamente")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Mantenimiento>> crear(@RequestBody Mantenimiento mantenimiento) {
        Mantenimiento nuevo = mantenimientoService.crear(mantenimiento);
        EntityModel<Mantenimiento> recurso = EntityModel.of(nuevo,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).buscarPorId(nuevo.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).listarTodos()).withRel("todos-los-mantenimientos"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).eliminar(nuevo.getId())).withRel("eliminar")
        );
        return ResponseEntity.status(201).body(recurso);
    }

    @Operation(summary = "Actualizar un mantenimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mantenimiento actualizado"),
            @ApiResponse(responseCode = "404", description = "Mantenimiento no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Mantenimiento>> actualizar(@PathVariable Long id, @RequestBody Mantenimiento mantenimiento) {
        Mantenimiento actualizado = mantenimientoService.actualizar(id, mantenimiento);
        EntityModel<Mantenimiento> recurso = EntityModel.of(actualizado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).buscarPorId(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).listarTodos()).withRel("todos-los-mantenimientos"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MantenimientoController.class).eliminar(id)).withRel("eliminar")
        );
        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Eliminar un mantenimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mantenimiento eliminado"),
            @ApiResponse(responseCode = "404", description = "Mantenimiento no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mantenimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}