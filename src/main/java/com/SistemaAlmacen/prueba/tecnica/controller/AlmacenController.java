package com.SistemaAlmacen.prueba.tecnica.controller;
import com.SistemaAlmacen.prueba.tecnica.service.InventarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/almacen")
public class AlmacenController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/home")
    @PreAuthorize("hasAuthority('ROLE_2')")
    public String homeAlmacen(Model model) {
        // Mostrar solo productos activos
        model.addAttribute("productos", inventarioService.listarProductosActivos());
        return "almacen/home"; // Nombre de la vista
    }

    @PostMapping("/salidas/{idProducto}")
    @PreAuthorize("hasAuthority('ROLE_2')")
    public String registrarSalida(
        @PathVariable Integer idProducto,
        @RequestParam int cantidad,
        HttpSession session,
        RedirectAttributes redirectAttributes // Para mensajes flash
    ) {
        // Obtener el ID del usuario
        Integer idUsuario = (Integer) session.getAttribute("userId");

        try {
            inventarioService.registrarSalida(idProducto, cantidad, idUsuario);
            redirectAttributes.addFlashAttribute("mensaje", "Salida registrada exitosamente");
        } catch (IllegalArgumentException e) {
            // Mostrar mensaje de error
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/almacen/home";
    }
}
