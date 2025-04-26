package com.SistemaAlmacen.prueba.tecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.SistemaAlmacen.prueba.tecnica.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RedireccionController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/redireccionar-por-rol")
    public String redireccionarSegunRol(Authentication authentication, HttpSession session) {
        
        // Obtener el rol del usuario
        GrantedAuthority authority = authentication.getAuthorities().iterator().next();
        String rol = authority.getAuthority();

        // Obtener el ID del usuario autenticado desde la base de datos usando el correo
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String correo = userDetails.getUsername(); // Obtener el correo del usuario
            Integer userId = usuarioService.obtenerIdPorCorreo(correo); // Consultar el ID en la BD
            session.setAttribute("userId", userId); // Guardar el ID en la sesión
        }

        // Redirigir según el rol
        return switch (rol) {
            case "ROLE_1" -> "redirect:/admin/home";
            case "ROLE_2" -> "redirect:/almacen/home";
            default -> "redirect:/";
        };
    }
}