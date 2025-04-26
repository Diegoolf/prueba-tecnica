package com.SistemaAlmacen.prueba.tecnica.controller;

import com.SistemaAlmacen.prueba.tecnica.dto.ProductoDto;
import com.SistemaAlmacen.prueba.tecnica.model.Producto;
import com.SistemaAlmacen.prueba.tecnica.service.InventarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InventarioService inventarioService;

    public AdminController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/home")
    public String homeAdmin(@RequestParam(required = false) Integer estatus, Model model) {
        model.addAttribute("productos", inventarioService.listarProductos(estatus));
        model.addAttribute("productoNuevo", new ProductoDto());
        return "admin/home";
    }

    @PostMapping("/productos")
    @PreAuthorize("hasAuthority('ROLE_1')")
    public String crearProducto(@Valid @ModelAttribute("productoNuevo") ProductoDto productoDto,
                                BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("productos", inventarioService.listarProductos(null));
            return "admin/home";
        }

        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setCantidad(0); // Inicializar cantidad
        producto.setEstatus(1); // Activo por defecto

        inventarioService.crearProducto(producto);
        return "redirect:/admin/home";
    }

   @PostMapping("/productos/{idProducto}/ajustar")
@PreAuthorize("hasAuthority('ROLE_1')")
public String ajustarCantidad(
    @PathVariable Integer idProducto,
    @RequestParam int cantidad,
    HttpSession session,
    RedirectAttributes redirectAttributes // Agregado como parámetro
) {
    Integer idUsuario = (Integer) session.getAttribute("userId");
    
    try {
        inventarioService.ajustarInventario(idProducto, cantidad, idUsuario);


        
        redirectAttributes.addFlashAttribute("mensaje", "entrada registrada exitosamente");
    } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/admin/home";
}

    @PostMapping("/productos/{idProducto}/estatus")
    @PreAuthorize("hasAuthority('ROLE_1')")
    public String cambiarEstatus(@PathVariable Integer idProducto, @RequestParam int estatus) {
        inventarioService.cambiarEstatus(idProducto, estatus);
        return "redirect:/admin/home";
    }

    
}
