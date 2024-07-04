package com.Mascoshop.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mascoshop.Entidades.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {
    Usuario findByNombreUsuario(String nombreUsuario);
    List<Usuario> findByRol_IdRolIn(List<Long> rolIds);
    long countByRol_IdRol(long rolId);
}