package com.automotora.service_ficha.controller;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automotora.service_ficha.model.FichaVehiculo;
import com.automotora.service_ficha.service.FichaVehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/fichas")
@Tag(name = "FichaVehiculo", description = "Operaciones relacionadas con fichas de vehículos con soporte HATEOAS")
public class FichaVehiculoController {

    @Autowired
    private FichaVehiculoService service;

    @Operation(summary = "Listar todas las fichas", description = "Devuelve una colección de fichas con enlaces")
    @GetMapping
    public CollectionModel<EntityModel<FichaVehiculo>> listar() {
        List<EntityModel<FichaVehiculo>> fichas = service.listarFichas().stream()
                .map(ficha -> EntityModel.of(ficha,
                        linkTo(methodOn(FichaVehiculoController.class).buscarPorId(ficha.getId())).withSelfRel(),
                        linkTo(methodOn(FichaVehiculoController.class).listar()).withRel("fichas")))
                .collect(Collectors.toList());

        return CollectionModel.of(fichas,
                linkTo(methodOn(FichaVehiculoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener ficha por ID", description = "Devuelve una ficha con enlaces a acciones relacionadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ficha encontrada"),
        @ApiResponse(responseCode = "404", description = "Ficha no encontrada")
    })
    @GetMapping("/{id}")
    public EntityModel<FichaVehiculo> buscarPorId(@PathVariable Long id) {
        FichaVehiculo ficha = service.buscarPorId(id).orElse(null);

        EntityModel<FichaVehiculo> recurso = EntityModel.of(ficha);

        recurso.add(linkTo(methodOn(FichaVehiculoController.class).buscarPorId(id)).withSelfRel());
        recurso.add(linkTo(methodOn(FichaVehiculoController.class).listar()).withRel("todas-las-fichas"));
        recurso.add(linkTo(methodOn(FichaVehiculoController.class).eliminarFicha(id)).withRel("eliminar"));

        return recurso;
    }

    @Operation(summary = "Crear ficha", description = "Crea una nueva ficha y devuelve enlaces")
    @PostMapping
    public ResponseEntity<EntityModel<FichaVehiculo>> crearFicha(@RequestBody FichaVehiculo ficha) {
        FichaVehiculo nuevaFicha = service.guardarFicha(ficha);

        EntityModel<FichaVehiculo> recurso = EntityModel.of(nuevaFicha,
                linkTo(methodOn(FichaVehiculoController.class).buscarPorId(nuevaFicha.getId())).withSelfRel());

        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Eliminar ficha", description = "Elimina una ficha por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFicha(@PathVariable Long id) {
        service.eliminarFicha(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Ficha con detalles de vehículo", description = "Devuelve una ficha junto con los datos del vehículo asociado")
    @GetMapping("/{id}/detalles")
    public Map<String, Object> obtenerFichaConVehiculo(@PathVariable Long id) {
        return service.obtenerFichaConVehiculo(id);
    }
}