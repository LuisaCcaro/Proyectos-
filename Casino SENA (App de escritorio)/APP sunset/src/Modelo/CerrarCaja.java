package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CerrarCaja {
    
    private static List<Pedido> pedidos = new ArrayList<>();

    public static void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public static double entrada() {
            MetodosEmpleados metodos = new MetodosEmpleados();

            double precioTotal = 0;
            for (Pedido pedido : pedidos) {
                try {
                    String categoria = pedido.getCategoria();
                    String producto = pedido.getProducto();
                    String cantidad = pedido.getCantidad();

                    if (!cantidad.isEmpty()) {
                        if (categoria.equals("Bebidas")) {
                            precioTotal += metodos.obtenerValorEntradaBeb(producto, cantidad) * Integer.parseInt(cantidad);
                        } else if (categoria.equals("Platos")) {
                            precioTotal += metodos.obtenerValorEntradaPlat(producto, cantidad) * Integer.parseInt(cantidad);
                        }
                    } else {
                        // Manejar el caso en que la cantidad está vacía
                        // Puedes imprimir un mensaje de error o tomar alguna otra acción apropiada
                    }

                } catch (SQLException ex) {
                    System.out.println("Error al obtener valor de entrada: " + ex.getMessage());
                }
            }
            return precioTotal;
        }
    
    public static double cajaYvalorActual() throws SQLException {
        
        MetodosEmpleados metodos = new MetodosEmpleados();
        double saldoInicial = metodos.obtenerSaldoInicial();
        return saldoInicial + entrada();
    }

    
    public static List<Pedido> getPedidos() {
        return pedidos;
    }

    public static void cerrarSesion() {
        pedidos.clear(); // Limpiar la lista de pedidos para iniciar una nueva sesión.
    }
    
}
