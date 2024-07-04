package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, Integer> {


    //Encontrar por categoria
    List<Producto> findByCategoriaProducto_IdCategoria(Integer categoriaId);
    //Encontrar por animal
    List<Producto> findByAnimal_IdAnimal(Integer animalId);
    //Encontrar por marca
    List<Producto> findByMarca_IdMarca(Integer marcaId);

    long count();

}

