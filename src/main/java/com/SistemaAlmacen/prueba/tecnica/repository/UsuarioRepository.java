package com.SistemaAlmacen.prueba.tecnica.repository;


import com.SistemaAlmacen.prueba.tecnica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
}



