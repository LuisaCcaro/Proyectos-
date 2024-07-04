package Modelo;

public class Pedido {
    
    int id_pedidos; 
    String mesa;  
    String mesero;  
    String categoria;   
    String producto;   
    String cantidad;    
    String estado;    
    String hora;    
    String precio;
    public Pedido() {
    }

    public Pedido(int id_pedidos, String mesa, String mesero, String categoria, String producto, String cantidad, String estado, String hora, String precio) {
        this.id_pedidos = id_pedidos;
        this.mesa = mesa;
        this.mesero = mesero;
        this.categoria = categoria;
        this.producto = producto;
        this.cantidad = cantidad;
        this.estado = estado;
        this.hora = hora;
        this.precio = precio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    

    public int getId_pedidos() {
        return id_pedidos;
    }

    public void setId_pedidos(int id_pedidos) {
        this.id_pedidos = id_pedidos;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    
}
