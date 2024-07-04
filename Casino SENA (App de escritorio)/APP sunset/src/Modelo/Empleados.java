package Modelo;

public class Empleados {
    public int id;
    public String nombre;
    public String apellido;
    public String ced;
    public String telefono;
    public String usuario;
    public String clave;
    public int rol;
    
    public Empleados(){
        
    }
    
    public Empleados(int id, String nombre, String apellido, String ced, String telefono, String usuario, String clave, int rol){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ced = ced;
        this.telefono = telefono;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCed() {
        return ced;
    }

    public void setCed(String ced) {
        this.ced = ced;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

}
