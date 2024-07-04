package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.Calificacion;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCalificacion extends JpaRepository <Calificacion, Integer> {
    List<Calificacion> findByProducto_IdProducto(Long idProducto);
}
