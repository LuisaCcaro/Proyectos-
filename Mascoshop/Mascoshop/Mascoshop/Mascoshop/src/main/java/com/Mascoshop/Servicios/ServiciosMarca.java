package com.Mascoshop.Servicios;

import com.Mascoshop.Entidades.Marca;
import com.Mascoshop.Entidades.Producto;
import com.Mascoshop.Repositorios.RepositorioMarca;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ServiciosMarca {

    private final RepositorioMarca repoMarca;

    public ServiciosMarca(RepositorioMarca repoMarca) {
        this.repoMarca = repoMarca;
    }

    //Buscar todas las marcas
    public List<Marca> marcasDisponibles() {
        return repoMarca.findAll();
    }

    // Agregar marca
    public Marca agregarNuevaMarca(Marca marca) {
        return repoMarca.save(marca);
    }

    //Actualizar
    public Marca actualizarMarca(Integer id, Marca marcaAcambiar) {
        return repoMarca.findById(id).map(marca -> {
            marca.setNombre(marcaAcambiar.getNombre());
            return repoMarca.save(marca);
        }).orElseThrow(() -> new RuntimeException("Ups, no existe!"));
    }
    //Borrar
    public void borrarMarca(Integer id){
        repoMarca.deleteById(id);
    }
}
