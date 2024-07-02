package com.Mascoshop.Entidades;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DetalleCompra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "idCompra")
    private Compra compra;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "subTotal")
    private Float subTotal;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
}