package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.ListaDeseos;
import com.Mascoshop.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface RepositorioListaDeseos extends JpaRepository<ListaDeseos, Integer> {
    Optional<ListaDeseos> findByUsuario_IdUsuario(Integer idUsuario);

}
