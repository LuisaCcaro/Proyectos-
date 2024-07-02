package com.Mascoshop.Servicios;

import com.Mascoshop.Entidades.Calificacion;
import com.Mascoshop.Repositorios.RepositorioCalificacion;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Data
@Service
public class ServiciosCalificacion {
    @Autowired
    private RepositorioCalificacion repositorioCalificacion;

    public ServiciosCalificacion(RepositorioCalificacion repositorioCalificacion) {
        this.repositorioCalificacion = repositorioCalificacion;
    }

    //Dar calificacion
    public Calificacion darCalificacion(Calificacion calificacion){
        return repositorioCalificacion.save(calificacion);
    }
    //Obtener todas
   public List<Calificacion> ObtenerTodaCalificacion(){
        return repositorioCalificacion.findAll();
   }
   // obtener solo las de un usuario
   public Optional<Calificacion> ObtenerCalificacionUsuario(Integer id){
        return repositorioCalificacion.findById(id);
   }
   // Calificacion editada
   public Calificacion actualizarCalificacion(Calificacion calificacion){
        return repositorioCalificacion.save(calificacion);
   }
   // Calificacion borrada.
   public void borrar(Integer id){
        repositorioCalificacion.deleteById(id);
   }
}
