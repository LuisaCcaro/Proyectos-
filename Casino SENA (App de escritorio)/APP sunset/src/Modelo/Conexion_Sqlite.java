package Modelo;

import java.sql.*;

public class Conexion_Sqlite {
    String url = "jdbc:sqlite:C:\\Users\\SENA\\Desktop\\proyect-sunset\\APP sunset\\sunset.db";
    Connection con;

    public Conexion_Sqlite(){
        try {  
            con = DriverManager.getConnection(url);
            System.out.println(con);
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la base de datos" + e);
        }

    }

    public Connection getConnection() {
        return con;
    }
    
    public void cerrarConexion() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e);
        }
    }
    
}
