package com.Mascoshop.dto;

import lombok.Data;

@Data
public class CompraDTO {
    private Integer idUsuario;
    private Integer idProducto;
    private int cantidad;
    private String metodoDepago;
    private Float total;
}
