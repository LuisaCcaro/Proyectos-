package com.Mascoshop.Servicios;

import com.Mascoshop.Entidades.ListaDeseos;
import com.Mascoshop.Entidades.ListaDeseosItem;
import com.Mascoshop.Entidades.Producto;
import com.Mascoshop.Entidades.Usuario;
import com.Mascoshop.Repositorios.RepositorioListaDeseos;
import com.Mascoshop.Repositorios.RepositorioListaDeseosItem;
import com.Mascoshop.Repositorios.RepositorioProducto;
import com.Mascoshop.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ServiciosListaDeseos {

    private final RepositorioListaDeseosItem listaDeseosItemRepository;
    private final RepositorioListaDeseos listaDeseosRepository;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioProducto repositorioProducto;

    public ServiciosListaDeseos(RepositorioListaDeseosItem listaDeseosItemRepository, RepositorioListaDeseos listaDeseosRepository, RepositorioUsuario repositorioUsuario, RepositorioProducto repositorioProducto) {
        this.listaDeseosItemRepository = listaDeseosItemRepository;
        this.listaDeseosRepository = listaDeseosRepository;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioProducto = repositorioProducto;
    }

    // Usuario agrega producto a la lista de deseos
    public ListaDeseos agregarProducto(Integer usuarioId, Integer productoId) {
        Usuario usuario = repositorioUsuario.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no existente"));
        ListaDeseos listaDeseos = listaDeseosRepository.findByUsuario_IdUsuario(usuarioId).orElse(new ListaDeseos());
        listaDeseos.setUsuario(usuario);
        Producto producto = repositorioProducto.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no existente"));
        ListaDeseosItem item = new ListaDeseosItem();
        item.setProducto(producto);
        listaDeseos.getItems().add(item); // Asegúrate de que la lista de ítems no sea nula
        item.setListaDeseos(listaDeseos);

        // Guardar primero la lista de deseos antes de guardar el ListaDeseosItem
        listaDeseosRepository.save(listaDeseos);
        listaDeseosItemRepository.save(item);

        return listaDeseos;
    }

    // Ver productos en la lista de deseos
    public List<ListaDeseosItem> verListaDeseos(Integer usuarioId) {
        ListaDeseos listaDeseos = listaDeseosRepository.findByUsuario_IdUsuario(Math.toIntExact(usuarioId)).orElseThrow(() -> new RuntimeException("Lista de deseos no existente"));
        return listaDeseos.getItems();
    }

    // Eliminar producto de la lista de deseos
    public void eliminarDeListaDeseos(Integer usuarioId, Integer itemId) {
        ListaDeseos listaDeseos = listaDeseosRepository.findByUsuario_IdUsuario(usuarioId).orElseThrow(() -> new RuntimeException("Lista de deseos no existente"));
        ListaDeseosItem item = listaDeseosItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item no existente"));
        listaDeseos.getItems().remove(item);
        listaDeseosItemRepository.delete(item);
        listaDeseosRepository.save(listaDeseos);
    }
}
