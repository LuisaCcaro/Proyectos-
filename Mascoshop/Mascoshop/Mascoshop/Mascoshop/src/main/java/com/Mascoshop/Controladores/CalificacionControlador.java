package com.Mascoshop.Controladores;

import com.Mascoshop.Entidades.Calificacion;
import com.Mascoshop.Servicios.ServiciosCalificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Calificar producto")
public class CalificacionControlador {

    @Autowired
    private ServiciosCalificacion serviciosCalificacion;

    // Crear calificacion.
    @PostMapping
    public ResponseEntity<Calificacion> crearCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion guardada = serviciosCalificacion.darCalificacion(calificacion);
        return new ResponseEntity<>(guardada, HttpStatus.CREATED);
    }

    // Obtener toda calificacion.
    @GetMapping
    public ResponseEntity<List<Calificacion>> ObtenerTodas() {
        List<Calificacion> calificaciones = serviciosCalificacion.ObtenerTodaCalificacion();
        return new ResponseEntity<>(calificaciones, HttpStatus.OK);
    }

    // Obtener calificacion por ID.
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> ObtenerPorIdCalificacion(@PathVariable Integer id) {
        Optional<Calificacion> calificacion = serviciosCalificacion.ObtenerCalificacionUsuario(id);

        return calificacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Editar calificacion.
    @PutMapping("/{id}")
    public ResponseEntity<Calificacion> editarCalificacion(@PathVariable Integer id, @RequestBody Calificacion calificacionEdit) {
        Optional<Calificacion> calificacionOptional = serviciosCalificacion.ObtenerCalificacionUsuario(id);

        if (calificacionOptional.isPresent()) {
            Calificacion calificacion = calificacionOptional.get();
            if (calificacionEdit.getComentario() != null) {
                calificacion.setCalificacion(calificacionEdit.getCalificacion());
                calificacion.setComentario(calificacionEdit.getComentario());
                Calificacion actualizada = serviciosCalificacion.actualizarCalificacion(calificacion);
                return new ResponseEntity<>(actualizada, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar calificacion.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCalificacion(@PathVariable Integer id) {
        if (serviciosCalificacion.ObtenerCalificacionUsuario(id).isPresent()) {
            serviciosCalificacion.borrar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
