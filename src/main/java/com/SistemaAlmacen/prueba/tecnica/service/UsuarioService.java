package com.SistemaAlmacen.prueba.tecnica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaAlmacen.prueba.tecnica.repository.UserRepository;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository usuarioRepository;

    public Integer obtenerIdPorCorreo(String correo) {
        return usuarioRepository.findIdByCorreo(correo);
    }
}