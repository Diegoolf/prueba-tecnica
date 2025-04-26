package com.SistemaAlmacen.prueba.tecnica.service;

import com.SistemaAlmacen.prueba.tecnica.model.Movimiento;
import com.SistemaAlmacen.prueba.tecnica.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;

    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public List<Movimiento> obtenerTodos() {
        return movimientoRepository.findAllWithRelations();
    }
}


