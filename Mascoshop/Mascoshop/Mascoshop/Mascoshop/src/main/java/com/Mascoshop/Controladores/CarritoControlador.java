package com.Mascoshop.Controladores;

import com.Mascoshop.Entidades.Carrito;
import com.Mascoshop.Entidades.CarritoItem;
import com.Mascoshop.Servicios.ServiciosCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Carrito")
public class CarritoControlador {

    private final ServiciosCarrito serviciosCarrito;

    @Autowired

    public CarritoControlador(ServiciosCarrito serviciosCarrito) {
        this.serviciosCarrito = serviciosCarrito;
    }

    //agregar al carro
    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarAlCarro(@RequestParam Integer usuarioId,@RequestParam Integer productoId){
        Carrito carrito = serviciosCarrito.agregarProducto(usuarioId, productoId);
        return new ResponseEntity<>(carrito, HttpStatus.OK);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<CarritoItem>> verCarrito(@PathVariable Integer usuarioId){
        List<CarritoItem> items = serviciosCarrito.encarrito(usuarioId);
        return  new ResponseEntity<>(items, HttpStatus.OK);
    }
    @DeleteMapping("/Eliminar")
    public ResponseEntity<Void>eliminarProductoEnCarrito(@RequestParam Integer usuarioId, @RequestParam Integer itemid){
        serviciosCarrito.eliminarDelCarro(usuarioId, itemid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
