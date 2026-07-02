package com.automotora.service_ventas.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automotora.service_ventas.config.ErrorResponse;
import com.automotora.service_ventas.model.Venta;
import com.automotora.service_ventas.service.VentaService;


import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/v1/ventas")
@CrossOrigin(origins = "*")
@Tag(name = "Ventas", description = "Endpoint para la gestión de ventas de vehículos con HATEOAS")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    @Operation(summary = "Registrar una nueva venta")
    public Venta crear(@RequestBody Venta venta) {
        return ventaService.guardarVenta(venta);
    }

    @GetMapping
    @Operation(summary = "Listar todas las ventas")
    public List<Venta> listar() {
        return ventaService.listarTodas();
    }

@GetMapping("/{id}")
@Operation(
    summary = "Obtener venta con links dinámicos a Cliente y FichaVehiculo",
    description = "Devuelve una venta enriquecida con datos de Cliente y FichaVehiculo"
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Venta encontrada",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Venta.class))),
    @ApiResponse(responseCode = "404", description = "Venta no encontrada",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
})
public EntityModel<Venta> obtenerUna(@PathVariable Long id) {
    Venta venta = ventaService.buscarPorId(id);

    EntityModel<Venta> recurso = EntityModel.of(venta);

    // Link a sí mismo
    recurso.add(linkTo(methodOn(VentaController.class).obtenerUna(id)).withSelfRel());

    // Link dinámico al microservicio Cliente (puerto 9002)
    String urlCliente = "http://localhost:9002/api/v1/clientes/" + venta.getClienteId();
    recurso.add(Link.of(urlCliente, "cliente"));

    // Link dinámico al microservicio FichaVehiculo (puerto 9003)
    String urlFicha = "http://localhost:9003/api/v1/fichas/" + venta.getFichaId();
    recurso.add(Link.of(urlFicha, "fichaVehiculo"));

    return recurso;
}

@Operation(
        summary = "Actualizar venta",
        description = "Actualiza una venta existente en la base de datos usando su ID y los nuevos datos enviados en el cuerpo de la petición."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venta.class))),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(
            @PathVariable Long id,
            @RequestBody Venta venta) {

        Venta actualizada = ventaService.actualizarVenta(id, venta);
        return ResponseEntity.ok(actualizada);
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta")
    public void eliminar(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
    }
}