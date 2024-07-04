
package Modelo;


public class UsuarioActual {
    
    private static String nombreUsuario;
    private static int idUser;

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static void setNombreUsuario(String nombreUsuario) {
        UsuarioActual.nombreUsuario = nombreUsuario;
    }

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int idUser) {
        UsuarioActual.idUser = idUser;
    }

    
}