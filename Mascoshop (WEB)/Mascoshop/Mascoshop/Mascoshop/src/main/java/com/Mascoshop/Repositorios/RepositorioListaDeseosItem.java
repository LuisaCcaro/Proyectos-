package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.ListaDeseos;
import com.Mascoshop.Entidades.ListaDeseosItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RepositorioListaDeseosItem extends JpaRepository <ListaDeseosItem, Integer> {
}
