package com.SistemaAlmacen.prueba.tecnica.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Data
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovimiento;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento; // "entrada" o "salida"

    @Column( name ="cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    // Relación ManyToOne opcional para obtener datos del usuario/producto
    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    private Producto producto;
}
