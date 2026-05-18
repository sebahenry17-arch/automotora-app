package com.automotora.service_financiamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automotora.service_financiamiento.model.Financiamiento;
import com.automotora.service_financiamiento.service.FinanciamientoService;

@RestController
@RequestMapping("/financiamientos")
public class FinanciamientoController {

    @Autowired
    private FinanciamientoService financiamientoService;

    @PostMapping
    public Financiamiento crear(@RequestBody Financiamiento financiamiento) {
        return financiamientoService.guardarFinanciamiento(financiamiento);
    }

    @GetMapping("/{id}")
    public Financiamiento verDetalle(@PathVariable Long id) {
        return financiamientoService.obtenerFinanciamiento(id);
    }

    @GetMapping
    public List<Financiamiento> listar() {
        return financiamientoService.listarFinanciamientos();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        financiamientoService.eliminarFinanciamiento(id);
    }
