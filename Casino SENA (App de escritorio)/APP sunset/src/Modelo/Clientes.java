package Modelo;

public class Clientes {
    int id_cliente;
    String nombre_clie;
    String ape_clie;
    String ced_ruc;
    String direccion_clie;
    String telef_clie;

    public Clientes() {
    }

    public Clientes(int id_cliente, String nombre_clie, String ape_clie, String ced_ruc, String direccion_clie, String telef_clie) {
        this.id_cliente = id_cliente;
        this.nombre_clie = nombre_clie;
        this.ape_clie = ape_clie;
        this.ced_ruc = ced_ruc;
        this.direccion_clie = direccion_clie;
        this.telef_clie = telef_clie;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_clie() {
        return nombre_clie;
    }

    public void setNombre_clie(String nombre_clie) {
        this.nombre_clie = nombre_clie;
    }

    public String getApe_clie() {
        return ape_clie;
    }

    public void setApe_clie(String ape_clie) {
        this.ape_clie = ape_clie;
    }

    public String getCed_ruc() {
        return ced_ruc;
    }

    public void setCed_ruc(String ced_ruc) {
        this.ced_ruc = ced_ruc;
    }

    public String getDireccion_clie() {
        return direccion_clie;
    }

    public void setDireccion_clie(String direccion_clie) {
        this.direccion_clie = direccion_clie;
    }

    public String getTelef_clie() {
        return telef_clie;
    }

    public void setTelef_clie(String telef_clie) {
        this.telef_clie = telef_clie;
    }
    
    
    
}
