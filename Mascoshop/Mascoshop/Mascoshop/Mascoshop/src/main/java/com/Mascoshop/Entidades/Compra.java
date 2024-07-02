package com.Mascoshop.Entidades;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompra;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;


    @JoinColumn(name = "Cantidad", nullable = false)
    private int cantidad;

    @Column(name = "metodoDepago", nullable = false)
    private String metodoDepago;

    //LocalDateTime representa tanto la fecha como la hora
    @Column(name = "FechaYHora", nullable = false)
    private LocalDateTime fechayhora;

    @Column(name = "total", nullable = false)
    private Float total;
}