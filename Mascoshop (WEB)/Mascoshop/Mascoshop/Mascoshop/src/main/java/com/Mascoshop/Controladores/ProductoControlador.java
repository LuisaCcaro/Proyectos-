package com.Mascoshop.Controladores;

import com.Mascoshop.Entidades.Animal;
import com.Mascoshop.Entidades.CategoriaProducto;
import com.Mascoshop.Entidades.Marca;
import com.Mascoshop.Entidades.Producto;
import com.Mascoshop.Servicios.ServiciosProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    private final ServiciosProductos serviciosProductos;

    @Autowired
    public ProductoControlador(ServiciosProductos serviciosProductos) {
        this.serviciosProductos = serviciosProductos;
    }


    @GetMapping("/count")
    public ResponseEntity<Long> contarProductos() {
        long count = serviciosProductos.contarProductos();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    // Listar los productos disponibles.
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = serviciosProductos.listarProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }


    // Listar por ID, Obtener producto.
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable Integer id) {
        Optional<Producto> producto = serviciosProductos.buscarProductoPorId(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Agregar un producto.
    @SuppressWarnings("null")
    @PostMapping("/agregar-producto")
    public ResponseEntity<?> agregarProducto(
        @RequestParam("nombre") String nombre,
        @RequestParam("categoriaProducto.idCategoria") Integer idCategoria,
        @RequestParam("animal.idAnimal") Integer idAnimal,
        @RequestParam("marca.idMarca") Integer idMarca,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("precio") Double precio,
        @RequestParam("cantidadDisponible") Integer cantidadDisponible,
        @RequestParam("imagen") MultipartFile imagen) {

    try {
        // Verificar que las entidades existen en la base de datos
        CategoriaProducto categoriaProducto = serviciosProductos.buscarCategoriaPorId(idCategoria);
        if (categoriaProducto == null) {
            return new ResponseEntity<>("Categoría no encontrada", HttpStatus.BAD_REQUEST);
        }

        Animal animal = serviciosProductos.buscarAnimalPorId(idAnimal);
        if (animal == null) {
            return new ResponseEntity<>("Animal no encontrado", HttpStatus.BAD_REQUEST);
        }

        Marca marca = serviciosProductos.buscarMarcaPorId(idMarca);
        if (marca == null) {
            return new ResponseEntity<>("Marca no encontrada", HttpStatus.BAD_REQUEST);
        }

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoriaProducto(categoriaProducto);
        producto.setAnimal(animal);
        producto.setMarca(marca);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCantidadDisponible(cantidadDisponible);

        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src", "main", "resources", "static", "img");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                // Verifica si el directorio existe, si no, créalo
                if (!Files.exists(directorioImagenes)) {
                    System.out.println("No existe la carpeta, procede a crearla");
                    Files.createDirectories(directorioImagenes);
                }else{
                    System.out.println("ya existe!");
                }
                
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta, imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                producto.setImagen(imagen.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        

        Producto nuevoProducto = serviciosProductos.agregarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    // Buscar producto por animal
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<Producto>> buscarPorAnimal(@PathVariable Integer animalId) {
        List<Producto> productos = serviciosProductos.encontrarPorAnimal(animalId);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Buscar producto por categoria
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable Integer categoriaId) {
        List<Producto> productos = serviciosProductos.encontrarPorCategoria(categoriaId);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Buscar producto por marca
    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Producto>> buscarPorMarca(@PathVariable Integer marcaId) {
        List<Producto> productos = serviciosProductos.encontrarPorMarca(marcaId);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Actualizar el producto.
    @PutMapping("/editar-producto/{id}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam("categoriaProducto.idCategoria") Integer idCategoria,
            @RequestParam("animal.idAnimal") Integer idAnimal,
            @RequestParam("marca.idMarca") Integer idMarca,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("cantidadDisponible") Integer cantidadDisponible,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        Optional<Producto> productoOptional = Optional.ofNullable(serviciosProductos.buscarPorId(id));

        if (productoOptional.isEmpty()) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }

        Producto producto = productoOptional.get();

        CategoriaProducto categoriaProducto = serviciosProductos.buscarCategoriaPorId(idCategoria);
        if (categoriaProducto == null) {
            return new ResponseEntity<>("Categoría no encontrada", HttpStatus.BAD_REQUEST);
        }

        Animal animal = serviciosProductos.buscarAnimalPorId(idAnimal);
        if (animal == null) {
            return new ResponseEntity<>("Animal no encontrado", HttpStatus.BAD_REQUEST);
        }

        Marca marca = serviciosProductos.buscarMarcaPorId(idMarca);
        if (marca == null) {
            return new ResponseEntity<>("Marca no encontrada", HttpStatus.BAD_REQUEST);
        }

        producto.setNombre(nombre);
        producto.setCategoriaProducto(categoriaProducto);
        producto.setAnimal(animal);
        producto.setMarca(marca);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCantidadDisponible(cantidadDisponible);

        // if (imagen != null && !imagen.isEmpty()) {
        //     Path directorioImagenes = Paths.get("src/main/resources/static/img");
        //     String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
        //     try {
        //         byte[] bytesImg = imagen.getBytes();
        //         Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
        //         Files.write(rutaCompleta, bytesImg);
        //         producto.setImagen(imagen.getOriginalFilename());
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //         return new ResponseEntity<>("Error al guardar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        //     }
        // }

        Producto productoActualizado = serviciosProductos.editarProducto(id, producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }


    @DeleteMapping("/eliminar-producto/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        try {
            if (serviciosProductos.existeProducto(id)) {
                serviciosProductos.borrarProducto(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            // Manejo de excepción específica
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de excepciones generales
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> obtenerCategoria(@PathVariable Integer id) {
    //     CategoriaProducto categoriaProducto = serviciosProductos.buscarCategoriaPorId(id);
    //     if (categoriaProducto == null) {
    //         return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
    //     }
    //     return new ResponseEntity<>(categoriaProducto, HttpStatus.OK);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Integer id) {
        try {
            Producto producto = serviciosProductos.buscarPorId(id);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    // Eliminar imagen de un producto
    @DeleteMapping("/borrar-imagen/{id}/imagen")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Integer id) {
        try {
            serviciosProductos.eliminarImagen(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Descargar una imagen de un producto
    @GetMapping("/descargar/{id}/imagen")
    public ResponseEntity<Resource> descargarImagen(@PathVariable Integer id) {
        try {
            String imagePath = serviciosProductos.obtenerRutaImagen(id);
            Path file = Paths.get(imagePath);
            if (!Files.exists(file) || !Files.isReadable(file)) {
                throw new RuntimeException("No se puede leer el archivo: " + imagePath);
            }
            Resource resource = new UrlResource(file.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName().toString() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
