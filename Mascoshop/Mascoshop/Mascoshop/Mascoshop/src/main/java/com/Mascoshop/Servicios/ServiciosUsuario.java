package com.Mascoshop.Servicios;

import com.Mascoshop.Entidades.Usuario;
import com.Mascoshop.Repositorios.RepositorioUsuario;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Data
@Service

public class ServiciosUsuario {

    private final RepositorioUsuario repoUsuario;

    /*Constructor*/
    @Autowired
    public ServiciosUsuario(RepositorioUsuario repoUsuario) {
        this.repoUsuario = repoUsuario;
    }
    
    public long contarClientes() {
        return repoUsuario.countByRol_IdRol(3L);
    }
    

    /*Listar usuarios; Método para listar los usuarios*/
    public List<Usuario> list(){
        return repoUsuario.findAll();
    }

    public List<Usuario> listarAdmins() {
        return repoUsuario.findByRol_IdRolIn(Arrays.asList(1L, 2L));
    }
    /*Buscar por id; Método para buscar por id*/
    public Usuario buscarPorId(Integer id){
        return  repoUsuario.findById(id).orElse(null);
    }
    /*Guardar un empleado en la base de datos, nuevo empleado*/
    public Usuario guardarUsuario(Usuario usuario){
        return repoUsuario.save(usuario);
    }
    /*Borrar usuario*/
    public void EliminarPorId(Integer id){
        repoUsuario.deleteById(id);
    }
}
