package com.Mascoshop.Controladores;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Mascoshop.Entidades.Rol;
import com.Mascoshop.Entidades.Usuario;
import com.Mascoshop.Repositorios.RepositorioUsuario;

@RestController
@AllArgsConstructor
@Data
public class AuthController {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequest request, HttpSession session) {
        request.limpiador();
        Usuario usuario = repositorioUsuario.findByNombreUsuario(request.getNombreUsuario());
        if (usuario != null && usuario.getContrasena().equals(request.getContrasena())) {
            if (usuario.getRol().getIdRol() == 1 || usuario.getRol().getIdRol() == 2) {
                session.setAttribute("usuario", usuario);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Administrador - login exitoso"));
            } else {
                if (usuario.getRol().getIdRol() == 3) {
                    session.setAttribute("usuario", usuario);
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("login exitoso"));
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage("credenciales incorrectas"));
        }
        return null;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        try {
            request.limpiador();
            Rol rolCliente = new Rol();
            rolCliente.setIdRol(3);
            Usuario nuevoUsuario = new Usuario(
                    0, request.getNombre(),
                    request.getApellido(),
                    request.getCorreo(),
                    request.getContrasena(),
                    request.getNombreUsuario(),
                    request.getDireccion(),
                    request.getTelefono(), rolCliente, null
            );
            Usuario nuevoUsuarioPersistido = repositorioUsuario.save(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "El usuario ya existe en la base de datos.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    public static class UsuarioRequest {
        @NotBlank(message = "El nombre no puede estar vacío")
        private String nombre;
        @NotBlank(message = "El apellido no puede estar vacío")
        private String apellido;
        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "El correo debe ser válido")
        private String correo;
        @NotBlank(message = "La contraseña no puede estar vacía")
        private String contrasena;
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        private String nombreUsuario;
        @NotBlank(message = "La dirección no puede estar vacía")
        private String direccion;
        @NotBlank(message = "El teléfono no puede estar vacío")
        private String telefono;
        private Rol rol;
        private String to;
        private String subject;
        private String message;

        // Métodos para limpiar entradas!
        public void limpiador() {
            this.nombre = cleanString(this.nombre);
            this.apellido = cleanString(this.apellido);
            this.correo = cleanString(this.correo);
            this.contrasena = cleanString(this.contrasena);
            this.nombreUsuario = cleanString(this.nombreUsuario);
            this.direccion = cleanString(this.direccion);
            this.telefono = cleanString(this.telefono);
        }

        private String cleanString(String input) {
            if (input != null) {
                return input.replaceAll("\\s+", "");
            }
            return null;
        }

        // Getters y setters
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public Rol getRol() {
            return rol;
        }

        public void setRol(Rol rol) {
            this.rol = rol;
        }
        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
    }
    }
}
