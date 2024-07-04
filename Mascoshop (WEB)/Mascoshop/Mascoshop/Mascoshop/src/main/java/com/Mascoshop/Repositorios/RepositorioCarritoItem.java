package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.Carrito;
import com.Mascoshop.Entidades.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioCarritoItem extends JpaRepository<CarritoItem, Integer> {
}
