package com.SistemaAlmacen.prueba.tecnica.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario") 
    private Integer idUsuario;
    
    @Column(unique = true, nullable = false)
    private String correo;
    
    @Column(nullable = false)
    private String contrasena;
    
    @Column(nullable = false)
    private Integer estatus;
    
    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;
    
    // Getters y Setters
}
