package com.Mascoshop.Repositorios;

import com.Mascoshop.Entidades.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAnimal extends JpaRepository<Animal, Integer> {
}
