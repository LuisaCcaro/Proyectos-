package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    String URL = "jdbc:sqlite:C:\\Users\\DIANA\\Desktop\\Reto-2\\Reto2\\Reto1\\base de datos\\Rayito-sol.db";
    Connection conec;

    public Conexion(){
        try {  
            conec = DriverManager.getConnection(URL);
            System.out.println("Rayo McQueen conectado..");
        } catch (SQLException e) {
            System.out.println("Error, No se pudo conectar" + e);
        }

    }
    
    public Connection getConnection() {
        return conec;
    }
        public void cerrarConexion() throws SQLException {
            if (conec != null && !conec.isClosed()) {
                conec.close();
                System.out.println("Conexión cerrada.");
            }
    }
}