package com.SistemaAlmacen.prueba.tecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SistemaAlmacen.prueba.tecnica.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u.idUsuario FROM Usuario u WHERE u.correo = :correo")
    Integer findIdByCorreo(String correo);
}