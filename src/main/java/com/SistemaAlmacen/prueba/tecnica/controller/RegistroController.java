package com.SistemaAlmacen.prueba.tecnica.controller;

import com.SistemaAlmacen.prueba.tecnica.dto.RegistroDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new RegistroDto());
        return "registro";
    }

    @PostMapping
    public String registrarUsuario(@Valid @ModelAttribute("usuario") RegistroDto registroDto, 
                                 BindingResult result,
                                 Model model) {
        
        if (result.hasErrors()) {
            return "registro";
        }
        
        try {
            jdbcTemplate.update(
                "INSERT INTO usuarios (nombre, correo, contrasena, estatus, idRol) VALUES (?, ?, ?, ?, ?)",
                registroDto.getNombre(),
                registroDto.getCorreo(),
                passwordEncoder.encode(registroDto.getContrasena()),
                true,
                2 // ID del rol ALMACENISTA
            );
            return "redirect:/login?registroExitoso";
            
        } catch (Exception e) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registro";
        }
    }
}
