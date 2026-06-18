package com.automotora.service_cliente.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automotora.service_cliente.model.Cliente;
import com.automotora.service_cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes con soporte HATEOAS")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes", description = "Devuelve una colección de clientes con enlaces")
    @GetMapping
    public CollectionModel<EntityModel<Cliente>> listar() {
        List<EntityModel<Cliente>> clientes = clienteService.obtenerClientes().stream()
                .map(cliente -> EntityModel.of(cliente,
                        linkTo(methodOn(ClienteController.class).obtener(cliente.getId())).withSelfRel(),
                        linkTo(methodOn(ClienteController.class).listar()).withRel("clientes")))
                .collect(Collectors.toList());

        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener cliente por ID", description = "Devuelve un cliente con enlaces a acciones relacionadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public EntityModel<Cliente> obtener(@PathVariable Long id) {
    Cliente cliente = clienteService.obtenerClientePorId(id);

    EntityModel<Cliente> recurso = EntityModel.of(cliente);

    // Link a sí mismo
    recurso.add(linkTo(methodOn(ClienteController.class).obtener(id)).withSelfRel());

    // Link a todos los clientes
    recurso.add(linkTo(methodOn(ClienteController.class).listar()).withRel("todos-los-clientes"));

    // Link para eliminar
    recurso.add(linkTo(methodOn(ClienteController.class).eliminar(id)).withRel("eliminar"));

    return recurso;
}

    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente y devuelve enlaces")
    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> crear(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.crearCliente(cliente);

        EntityModel<Cliente> recurso = EntityModel.of(nuevoCliente,
                linkTo(methodOn(ClienteController.class).obtener(nuevoCliente.getId())).withSelfRel());

        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
