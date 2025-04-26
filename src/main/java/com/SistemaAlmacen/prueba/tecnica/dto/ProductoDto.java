package com.SistemaAlmacen.prueba.tecnica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Size(max = 255, message = "Máximo 255 caracteres")
    private String descripcion;
}
