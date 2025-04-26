package com.SistemaAlmacen.prueba.tecnica.service;

import com.SistemaAlmacen.prueba.tecnica.model.Producto;
import com.SistemaAlmacen.prueba.tecnica.model.Movimiento;
import com.SistemaAlmacen.prueba.tecnica.repository.ProductoRepository;
import com.SistemaAlmacen.prueba.tecnica.repository.MovimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class InventarioService {

    private final ProductoRepository productoRepository;
    private final MovimientoRepository movimientoRepository;

    public InventarioService(ProductoRepository productoRepository, MovimientoRepository movimientoRepository) {
        this.productoRepository = productoRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public void ajustarInventario(Integer idProducto, int cantidad, Integer idUsuario) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Solo se permiten entradas de productos (cantidad positiva)");
        }
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        int cantidadAnterior = producto.getCantidad();
        int nuevaCantidad = cantidadAnterior + cantidad;

        producto.setCantidad(nuevaCantidad);
        productoRepository.save(producto);

        // Registrar el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setIdProducto(idProducto);
        movimiento.setIdUsuario(idUsuario);
        movimiento.setTipoMovimiento(cantidad > 0 ? "entrada" : "salida"); // Correcto: cantidad es el ajuste
        movimiento.setCantidad(Math.abs(cantidad)); // Correcto: cantidad es el ajuste
        movimiento.setFechaHora(LocalDateTime.now());
        movimientoRepository.save(movimiento);
    }

    @Transactional
    public void cambiarEstatus(Integer idProducto, int estatus) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstatus(estatus);
        productoRepository.save(producto);
    }

    public List<Producto> listarProductos(Integer estatus) {
        return estatus != null ? productoRepository.findByEstatus(estatus) : productoRepository.findAll();
    }
    public List<Producto> listarProductosActivos() {
        return productoRepository.findByEstatus(1);
    }

     @Transactional
    public void registrarSalida(Integer idProducto, int cantidad, Integer idUsuario) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getCantidad() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock del producto: " + producto.getNombre());
        }
        // Registrar el movimiento (salida)
        Movimiento movimiento = new Movimiento();
        movimiento.setIdProducto(idProducto);
        movimiento.setIdUsuario(idUsuario);
        movimiento.setTipoMovimiento("salida");
        movimiento.setCantidad(cantidad);
        movimiento.setFechaHora(LocalDateTime.now());

        movimientoRepository.save(movimiento);

        // Actualizar el stock del producto
        producto.setCantidad(producto.getCantidad() - cantidad);
        productoRepository.save(producto);
    }
}
