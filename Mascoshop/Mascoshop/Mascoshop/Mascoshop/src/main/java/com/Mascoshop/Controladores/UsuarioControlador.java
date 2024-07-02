package com.Mascoshop.Controladores;

import com.Mascoshop.Entidades.Usuario;
import com.Mascoshop.Repositorios.RepositorioUsuario;
import com.Mascoshop.Servicios.ServiciosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/en/usuarios")
public class UsuarioControlador {

    private final ServiciosUsuario serviciosUsuario;

    @Autowired
    public UsuarioControlador(ServiciosUsuario serviciosUsuario, RepositorioUsuario usuarioRepository) {
        this.serviciosUsuario = serviciosUsuario;
    }

    @GetMapping("/countClientes")
        public ResponseEntity<Long> contarClientes() {
        long count = serviciosUsuario.contarClientes();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //Listar todos los usuarios :D
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = serviciosUsuario.list();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @GetMapping("/admin")
    public ResponseEntity<List<Usuario>> listarAdmins() {
        List<Usuario> admins = serviciosUsuario.listarAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    //buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = serviciosUsuario.buscarPorId(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //Actualizar un usuario existente.
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = serviciosUsuario.buscarPorId(id);
        if (usuario != null) {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setCorreo(usuarioActualizado.getCorreo());
            usuario.setContrasena(usuarioActualizado.getContrasena());
            usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            usuario.setDireccion(usuarioActualizado.getDireccion());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setRol(usuarioActualizado.getRol());
            Usuario usuarioGuardado = serviciosUsuario.guardarUsuario(usuario);
            return new ResponseEntity<>(usuarioGuardado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id){
        if (serviciosUsuario.buscarPorId(id) != null){
            serviciosUsuario.EliminarPorId(id);
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
