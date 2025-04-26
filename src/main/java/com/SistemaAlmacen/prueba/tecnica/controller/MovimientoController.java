package com.SistemaAlmacen.prueba.tecnica.controller;

import com.SistemaAlmacen.prueba.tecnica.service.MovimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovimientoController {
    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/movimientos")
    public String mostrarMovimientos(Model model) {
        model.addAttribute("movimientos", movimientoService.obtenerTodos());
        return "movimientos";
    }
}
