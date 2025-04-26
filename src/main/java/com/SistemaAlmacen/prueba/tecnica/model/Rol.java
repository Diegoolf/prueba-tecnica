package com.SistemaAlmacen.prueba.tecnica.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Rol {
    @Id
    private Integer idRol;

    private String nombreRol;
}


