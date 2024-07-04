package Modelo;

class Item {
    String producto;
    int cantidad;
    double totalPrice;

    public Item(String producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.totalPrice = precio;
    }
}