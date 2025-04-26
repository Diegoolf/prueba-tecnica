package com.SistemaAlmacen.prueba.tecnica.repository;

import com.SistemaAlmacen.prueba.tecnica.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    
    @Query("SELECT m FROM Movimiento m LEFT JOIN FETCH m.producto LEFT JOIN FETCH m.usuario")
    List<Movimiento> findAllWithRelations();
}
