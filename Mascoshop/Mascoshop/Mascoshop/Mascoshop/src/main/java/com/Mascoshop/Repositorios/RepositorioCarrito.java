package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioCarrito  extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByUsuario_IdUsuario(Integer id);
}
