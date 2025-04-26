package com.SistemaAlmacen.prueba.tecnica.repository;

import com.SistemaAlmacen.prueba.tecnica.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByEstatus(Integer estatus);
}
