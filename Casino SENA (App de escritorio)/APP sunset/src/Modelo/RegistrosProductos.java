/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author SENA
 */
public class RegistrosProductos {
      int id_producto;
    String nombre;
    String cantidad;
    String fecha;

    public RegistrosProductos() {
    }

    public RegistrosProductos(int id_producto, String nombre, String cantidad, String fecha) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
    

