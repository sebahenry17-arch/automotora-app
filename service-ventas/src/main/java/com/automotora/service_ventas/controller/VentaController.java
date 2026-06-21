package com.automotora.service_ventas.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import com.automotora.service_ventas.model.Venta;
import com.automotora.service_ventas.service.VentaService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/ventas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
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
    @Operation(summary = "Obtener venta con links dinámicos a Cliente y FichaVehiculo")
    public EntityModel<Venta> obtenerUna(@PathVariable Long id) {
        // 1. Buscar la venta
        Venta venta = ventaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        // 2. Crear recurso HATEOAS
        EntityModel<Venta> recurso = EntityModel.of(venta);

        // 3. Link a sí mismo
        recurso.add(linkTo(methodOn(VentaController.class).obtenerUna(id)).withSelfRel());

        // 4. Link dinámico al microservicio de Clientes (puerto 9002)
        String urlCliente = "http://localhost:9002/api/v1/clientes/" + venta.getClienteId();
        recurso.add(Link.of(urlCliente).withRel("detalle-cliente"));

        // 5. Link dinámico al microservicio de Fichas (puerto 9003)
        String urlFicha = "http://localhost:9003/api/v1/fichas/" + venta.getFichaId();
        recurso.add(Link.of(urlFicha).withRel("detalle-ficha"));

        return recurso;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta")
    public void eliminar(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
    }
}