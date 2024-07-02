package com.Mascoshop.Servicios;

import com.Mascoshop.Entidades.Carrito;
import com.Mascoshop.Entidades.CarritoItem;
import com.Mascoshop.Entidades.Producto;
import com.Mascoshop.Entidades.Usuario;
import com.Mascoshop.Repositorios.RepositorioCarrito;
import com.Mascoshop.Repositorios.RepositorioCarritoItem;
import com.Mascoshop.Repositorios.RepositorioProducto;
import com.Mascoshop.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ServiciosCarrito {
    private final RepositorioCarritoItem repoItem;
    private final RepositorioCarrito reposCarrito;
    private final RepositorioUsuario reposUsuario;
    private final RepositorioProducto reposProducto;

    public ServiciosCarrito(RepositorioCarritoItem repoItem, RepositorioCarrito reposCarrito, RepositorioUsuario reposUsuario, RepositorioProducto reposProducto) {
        this.repoItem = repoItem;
        this.reposCarrito = reposCarrito;
        this.reposUsuario = reposUsuario;
        this.reposProducto = reposProducto;
    }

    //Usuario agrega producto al carrito

    public Carrito agregarProducto(Integer usuarioId, Integer productoId) {
        Usuario usuario = reposUsuario.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no existente"));
        Carrito carrito = reposCarrito.findByUsuario_IdUsuario(usuarioId).orElse(new Carrito());
        carrito.setUsuario(usuario);
        Producto producto = reposProducto.findById(productoId).orElseThrow(() ->new RuntimeException("Producto no existente"));
        CarritoItem item = new CarritoItem();
        item.setProducto(producto);
        carrito.getItems().add(item); // Asegúrate de que la lista de ítems no sea nula
        item.setCarrito(carrito);

        // Guardar primero el carrito antes de guardar el CarritoItem
        reposCarrito.save(carrito);
        repoItem.save(item);

        return carrito;
    }
//    public Carrito agregarProducto(Integer usuarioId, Integer productoId) {
//        Usuario usuario = reposUsuario.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no existente"));
//        Carrito carrito = reposCarrito.findByUsuario_IdUsuario(usuarioId).orElse(new Carrito());
//        carrito.setUsuario(usuario);
//        Producto producto = reposProducto.findById(productoId).orElseThrow(() ->new RuntimeException("Producto no existente"));
//        CarritoItem item = new CarritoItem();
//        item.setProducto(producto);
//        carrito.getItems().add((item));
//        item.setCarrito(carrito);
//        repoItem.save(item);
//        return reposCarrito.save(carrito);
//    }
    //Ver productos
    public List<CarritoItem> encarrito(Integer UsuarioId){
        Carrito carrito = reposCarrito.findByUsuario_IdUsuario(UsuarioId).orElseThrow(() -> new RuntimeException("Carrito no existente"));
        return carrito.getItems();
    }

    //Eliminar de carrito
    public void eliminarDelCarro(Integer usuarioId, Integer itemId){
        Carrito carrito = reposCarrito.findByUsuario_IdUsuario(usuarioId).orElseThrow(()->new RuntimeException("Carrito no existente"));
        CarritoItem item = repoItem.findById(itemId).orElseThrow(()-> new RuntimeException("Item no existente"));
        carrito.getItems().remove(item);
        repoItem.delete(item);
        reposCarrito.save(carrito);
    }
}
