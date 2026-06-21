package com.automotora.service_financiamiento.controller;

import com.automotora.service_financiamiento.config.ErrorResponse;
import com.automotora.service_financiamiento.model.Financiamiento;
import com.automotora.service_financiamiento.service.FinanciamientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/financiamientos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Financiamientos", description = "Gestión de financiamientos con HATEOAS")
public class FinanciamientoController {

    @Autowired
    private FinanciamientoService financiamientoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo financiamiento")
    public Financiamiento crear(@RequestBody Financiamiento financiamiento) {
        return financiamientoService.guardar(financiamiento);
    }

    @GetMapping
    @Operation(summary = "Listar todos los financiamientos")
    public List<Financiamiento> listar() {
        return financiamientoService.listarTodos();
    }

@GetMapping("/{id}")
@Operation(
    summary = "Obtener financiamiento por ID",
    description = "Devuelve un financiamiento con datos enriquecidos de Cliente y Venta"
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Financiamiento encontrado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Financiamiento.class))),
    @ApiResponse(responseCode = "404", description = "Financiamiento no encontrado",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
})
public EntityModel<Financiamiento> obtenerUno(@PathVariable Long id) {
    Financiamiento f = financiamientoService.buscarPorId(id);

    EntityModel<Financiamiento> recurso = EntityModel.of(f);
    recurso.add(linkTo(methodOn(FinanciamientoController.class).obtenerUno(id)).withSelfRel());
    recurso.add(Link.of("http://localhost:9002/api/v1/clientes/" + f.getClienteId(), "cliente"));
    recurso.add(Link.of("http://localhost:9004/api/v1/ventas/" + f.getVentaId(), "venta"));

    return recurso;
}

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un financiamiento existente")
    public Financiamiento actualizar(@PathVariable Long id, @RequestBody Financiamiento financiamiento) {
        return financiamientoService.actualizar(id, financiamiento);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un financiamiento")
    public void eliminar(@PathVariable Long id) {
        financiamientoService.eliminar(id);
    }
}