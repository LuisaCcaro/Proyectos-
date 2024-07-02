package com.Mascoshop.Servicios;

import com.Mascoshop.Entidades.Animal;
import com.Mascoshop.Entidades.CategoriaProducto;
import com.Mascoshop.Entidades.Marca;
import com.Mascoshop.Entidades.Producto;
import com.Mascoshop.Repositorios.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class ServiciosProductos {
    private final RepositorioProducto repoProducto;
    private final RepositorioCalificacion reposCalificacion;
    private final RepositorioAnimal repositorioAnimal;
    private final RepositorioMarca repositorioMarca;
    private final RepositorioCategoria repositorioCategoria;

    public ServiciosProductos(RepositorioProducto repoProducto, RepositorioCalificacion reposCalificacion, RepositorioAnimal repositorioAnimal, RepositorioMarca repositorioMarca, RepositorioCategoria repositorioCategoria) {
        this.repoProducto = repoProducto;
        this.reposCalificacion = reposCalificacion;
        this.repositorioAnimal = repositorioAnimal;
        this.repositorioMarca = repositorioMarca;
        this.repositorioCategoria = repositorioCategoria;
    }

    /*Listar productos*/
    public List<Producto> listarProductos() {
        return repoProducto.findAll();
    }
    

    public long contarProductos() {
        return repoProducto.count();
    }
    
    /*Buscar por animal, listar*/
    public List<Producto> encontrarPorAnimal(Integer animalId){
        return repoProducto.findByAnimal_IdAnimal(animalId);
    }

    /*Buscar por categoria*/
    public List<Producto> encontrarPorCategoria(Integer categoriaId){
        return repoProducto.findByCategoriaProducto_IdCategoria(categoriaId);
    }

    /*Buscar por Marca*/
    public List<Producto> encontrarPorMarca(Integer marcaId){
        return repoProducto.findByMarca_IdMarca(marcaId);
    }

    // Buscar categoría por ID
    public CategoriaProducto buscarCategoriaPorId(Integer idCategoria) {
        return repositorioCategoria.findById(idCategoria).orElse(null);
    }

    // Buscar animal por ID
    public Animal buscarAnimalPorId(Integer idAnimal) {
        return repositorioAnimal.findById(idAnimal).orElse(null);
    }

    // Buscar marca por ID
    public Marca buscarMarcaPorId(Integer idMarca) {
        return repositorioMarca.findById(idMarca).orElse(null);
    }


    public Optional<Producto> buscarProductoPorId(Integer id) {
        return repoProducto.findById(id);
    }

    // Buscar por ID tal producto.
    public Producto buscarPorId(Integer id) {
        System.out.println("Buscando producto en el servicio con ID: " + id); // Log adicional
        Optional<Producto> producto = repoProducto.findById(id);
        if (producto.isPresent()) {
            System.out.println("Producto encontrado en el servicio: " + producto.get()); // Log adicional
            return producto.get();
        } else {
            System.out.println("Producto no encontrado en el servicio para ID: " + id); // Log adicional
            throw new RuntimeException("Producto no encontrado");
        }
    }

    // Agregar producto
    public Producto agregarProducto(Producto producto) {
        return repoProducto.save(producto);
    }

    // Actualizar producto
    public Producto editarProducto(Integer id, Producto productoEditar){
        return repoProducto.findById(id).map(producto -> {
            producto.setNombre(productoEditar.getNombre());
            producto.setCategoriaProducto(productoEditar.getCategoriaProducto());
            producto.setAnimal(productoEditar.getAnimal());
            producto.setMarca(productoEditar.getMarca());
            producto.setDescripcion(productoEditar.getDescripcion());
            producto.setImagen(productoEditar.getImagen());
            producto.setCantidadDisponible(productoEditar.getCantidadDisponible());
            return repoProducto.save(producto);
        }).orElseThrow(() -> new RuntimeException("Ups, producto no encontrado"));
    }

    // Obtener ruta de imagen
    public String obtenerRutaImagen(Integer id) {
        Producto producto = repoProducto.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return producto.getImagen();
    }
    
    public void borrarProducto(Integer id) {
        try {
            repoProducto.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el producto con ID: " + id, e);
        }
    }
    public boolean existeProducto(Integer id) {
        return repoProducto.existsById(id);
    }

    // Eliminar una imagen correspondiente a un producto.
    public void eliminarImagen(Integer id) {
        Producto producto = repoProducto.findById(id).orElseThrow(() -> new RuntimeException("No encontrado."));
        String imagePath = producto.getImagen();
        if (imagePath != null && !imagePath.isEmpty()) {
            File imagefile = new File(imagePath);
            if (imagefile.exists()) {
                imagefile.delete();
            }
        }
        producto.setImagen(null);
        repoProducto.save(producto);
    }
}
