package com.Mascoshop.Entidades;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ListaDeseos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaDeseos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idListaDeseos;

    @ManyToOne
    @JoinColumn(name = "idUsuario",nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "listaDeseos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaDeseosItem> items = new ArrayList<>(); // Inicializar la lista aquí
}