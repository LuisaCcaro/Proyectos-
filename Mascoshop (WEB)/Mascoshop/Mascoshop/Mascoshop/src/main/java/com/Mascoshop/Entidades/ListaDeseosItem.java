package com.Mascoshop.Entidades;

import javax.persistence.*;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ListaDeseosItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaDeseosItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idListaDeseos", nullable = false)
    private ListaDeseos listaDeseos;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
}
