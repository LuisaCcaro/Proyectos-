package com.Mascoshop.Controladores;

import com.Mascoshop.Entidades.Marca;
import com.Mascoshop.Servicios.ServiciosMarca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Marcas")
public class MarcaControl {

    private final ServiciosMarca serviciosMarca;

    @Autowired

    public MarcaControl(ServiciosMarca serviciosMarca) {
        this.serviciosMarca = serviciosMarca;
    }

    //Listar las marcas que se disponen

    @GetMapping
    public ResponseEntity<List<Marca>> listarMarcas() {
        List<Marca> marcas = serviciosMarca.marcasDisponibles();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    //Actualizar una marca ._.
    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Integer id, @RequestBody Marca marcaActualiada) {
        try {
            Marca marca = serviciosMarca.actualizarMarca(id, marcaActualiada);
            return new ResponseEntity<>(marca, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable Integer id){
        serviciosMarca .borrarMarca(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
