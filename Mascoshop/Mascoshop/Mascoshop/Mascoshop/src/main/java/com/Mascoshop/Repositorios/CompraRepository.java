package com.Mascoshop.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mascoshop.Entidades.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
}

