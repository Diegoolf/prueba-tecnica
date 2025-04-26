package com.SistemaAlmacen.prueba.tecnica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String mostrarLogin(
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout,
        Model model) {
        
        // Manejo de errores
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Intente nuevamente.");
        }
        
        // Confirmación de logout
        if (logout != null) {
            model.addAttribute("mensaje", "Ha cerrado sesión exitosamente.");
        }
        
        return "login"; // Nombre de tu plantilla (login.html)
    }

    // Opcional: Manejo específico de errores (alternativa al parámetro en el método anterior)
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }
}


