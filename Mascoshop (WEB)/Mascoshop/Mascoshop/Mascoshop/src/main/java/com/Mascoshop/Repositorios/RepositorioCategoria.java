package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCategoria extends JpaRepository <CategoriaProducto, Integer> {
}
