package com.Mascoshop.Servicios;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mascoshop.Entidades.Compra;
import com.Mascoshop.Entidades.Producto;
import com.Mascoshop.Entidades.Usuario;
import com.Mascoshop.Repositorios.CompraRepository;
import com.Mascoshop.Repositorios.RepositorioProducto;
import com.Mascoshop.Repositorios.RepositorioUsuario;
import com.Mascoshop.dto.CompraDTO;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private RepositorioUsuario usuarioRepository;

    @Autowired
    private RepositorioProducto productoRepository;

    public Compra crearCompra(CompraDTO compraDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(compraDTO.getIdUsuario())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        Producto producto = productoRepository.findById(compraDTO.getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setProducto(producto);
        compra.setCantidad(compraDTO.getCantidad());
        compra.setMetodoDepago(compraDTO.getMetodoDepago());
        compra.setFechayhora(LocalDateTime.now());
        compra.setTotal(compraDTO.getTotal());

        return compraRepository.save(compra);
    }
}

