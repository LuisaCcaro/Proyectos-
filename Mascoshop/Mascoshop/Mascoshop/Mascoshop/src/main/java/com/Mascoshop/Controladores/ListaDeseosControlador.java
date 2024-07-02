package com.Mascoshop.Controladores;

import com.Mascoshop.Entidades.ListaDeseos;
import com.Mascoshop.Entidades.ListaDeseosItem;
import com.Mascoshop.Servicios.ServiciosListaDeseos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listadeseos")
public class ListaDeseosControlador {

    private final ServiciosListaDeseos serviciosListaDeseos;

    @Autowired
    public ListaDeseosControlador(ServiciosListaDeseos serviciosListaDeseos) {
        this.serviciosListaDeseos = serviciosListaDeseos;
    }


    // Agregar a la lista de deseos
    @PostMapping("/agregar")
    public ResponseEntity<ListaDeseos> agregarALaListaDeseos(@RequestParam Integer usuarioId, @RequestParam Integer productoId) {
        ListaDeseos listaDeseos = serviciosListaDeseos.agregarProducto(usuarioId, productoId);
        return new ResponseEntity<>(listaDeseos, HttpStatus.CREATED);
    }

    // Ver lista de deseos
    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<ListaDeseosItem>> verListaDeseos(@PathVariable Integer usuarioId) {
        List<ListaDeseosItem> items = serviciosListaDeseos.verListaDeseos(usuarioId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Eliminar de la lista de deseos
    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarProductoDeListaDeseos(@RequestParam Integer usuarioId, @RequestParam Integer itemId) {
        serviciosListaDeseos.eliminarDeListaDeseos(usuarioId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
