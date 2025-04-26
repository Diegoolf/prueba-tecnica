package com.SistemaAlmacen.prueba.tecnica.dto;

import jakarta.validation.constraints.*;

public class RegistroDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "Mínimo 8 caracteres")
    private String contrasena;
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
