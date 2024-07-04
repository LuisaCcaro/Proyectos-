package Modelo;

public class Productos {
    int id_producto;
    String nombre;
    String cantidad;
    String precio;
    
    public Productos(){
        
    }
    
    public Productos(int id_producto, String nombre, String cantidad, String precio){
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    
    
}
