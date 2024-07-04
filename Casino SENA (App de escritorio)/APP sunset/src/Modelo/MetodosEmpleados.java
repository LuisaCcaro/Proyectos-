package Modelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MetodosEmpleados {

    Conexion_Sqlite con = new Conexion_Sqlite();
    Connection cn;

    public MetodosEmpleados() {
        cn = con.getConnection();
    }

    public String mostrarhora() {

        LocalDateTime hora = LocalDateTime.now();
        DateTimeFormatter formatohora = DateTimeFormatter.ofPattern("hh:mm:ss");
        String horaActual = hora.format(formatohora);

        return horaActual;

    }

    public String mostrarfecha() {

        LocalDateTime fechita = LocalDateTime.now();
        DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaActual = fechita.format(fecha);

        return fechaActual;

    }

    public void registrarAccionEmpleado(String accion) throws SQLException {

        int usuario = UsuarioActual.getIdUser();
        int idAccion = obtenerIdAccion(accion);
        String fecha = mostrarfecha();
        String hora = mostrarhora();

        // Consulta SQL para insertar una acción de empleado
        if (idAccion != 0) {
            String sql = "INSERT INTO aud_empleado (usuario, hora, fecha, accion) VALUES (?, ?, ?, ?)";

            try (
                    PreparedStatement ps = cn.prepareStatement(sql)) {
                // Asignar parámetros
                ps.setInt(1, usuario);
                ps.setString(2, hora);
                ps.setString(3, fecha);
                ps.setInt(4, idAccion);
                // Ejecutar la consulta
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Acción no encontrada");
        }
    }

    // Método para obtener el ID de la acción a partir de su nombre
    private int obtenerIdAccion(String nombreAccion) {

        String sql = "SELECT id_desc_accion FROM emple_accion WHERE nombre = ?";

        try (
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombreAccion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_desc_accion");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0; // Si no se encuentra la acción, retornamos -1
    }

    public void insertarCierreCaja(String idUsuario, double saldoInicial, double entrada, double caja, double valorActual) throws SQLException {
        try {
            // Consulta para insertar el registro
            String sql = "INSERT INTO aud_cierre_caja (usuario, saldo_inicio, entrada, caja, valor_actual, hora_cierre, fecha_cierre) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, idUsuario);
            ps.setDouble(2, saldoInicial);
            ps.setDouble(3, entrada);
            ps.setDouble(4, caja);
            ps.setDouble(5, valorActual);

            // Obtener la hora y fecha de cierre
            String horaCierre = mostrarhora();
            String fechaCierre = mostrarfecha();

            // Establecer los valores de hora y fecha de cierre en la consulta
            ps.setString(6, horaCierre);
            ps.setString(7, fechaCierre);

            // Ejecutar la consulta
            ps.executeUpdate();

            // Cerrar recursos
            ps.close();
            System.out.println("Cierre de caja insertado correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta: " + ex.getMessage());
            throw ex; // Lanzar la excepción para que sea manejada en un nivel superior
        }
    }

    public double obtenerValorEntradaBeb(String nombreBebida, String cantidad) throws SQLException {

        // Consulta para obtener el precio del producto
        String sql = "SELECT precio FROM prod_bebidas WHERE nombre = ?";

        // Preparar la consulta
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, nombreBebida);

        // Ejecutar la consulta y obtener el resultado
        ResultSet rs = ps.executeQuery();
        double precio = 0;
        if (rs.next()) {
            precio = rs.getDouble("precio");
        }

        // Cerrar recursos
        rs.close();
        ps.close();

        return precio;
    }

    public double obtenerValorEntradaPlat(String nombrePlato, String cantidad) throws SQLException {

        // Consulta para obtener el precio del producto
        String sql = "SELECT precio FROM prod_platos WHERE nombre = ?";

        // Preparar la consulta
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, nombrePlato);

        // Ejecutar la consulta y obtener el resultado
        ResultSet rs = ps.executeQuery();
        double precio = 0;
        if (rs.next()) {
            precio = rs.getDouble("precio");
        }

        // Cerrar recursos
        rs.close();
        ps.close();

        return precio;

    }

    public int obtenerIdUsuario(String usuario) throws SQLException {

        // Consulta para obtener el ID del usuario
        String sql = "SELECT id_emple FROM reg_empleados WHERE usuario = ?";

        // Preparar la consulta
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, usuario);

        // Ejecutar la consulta y obtener el resultado
        ResultSet rs = ps.executeQuery();
        int idUsuario = -1;
        if (rs.next()) {
            idUsuario = rs.getInt("id_emple");
        }

        // Cerrar recursos
        rs.close();
        ps.close();

        return idUsuario;
    }

    public String obtenerNom(String usuario) throws SQLException {
        String nombreUsuario = null;
        String sql = "SELECT nom_emple FROM reg_empleados WHERE usuario = ?";

        // Preparar la consulta
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, usuario);

        // Ejecutar la consulta y obtener el resultado
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nombreUsuario = rs.getString("nom_emple");
        }

        // Cerrar recursos
        rs.close();
        ps.close();

        return nombreUsuario;
    }

    public double obtenerSaldoInicial() throws SQLException {

        // Consulta para obtener el saldo inicial
        String sql = "SELECT monto FROM reg_saldo_inicial";

        // Preparar la consulta
        PreparedStatement ps = cn.prepareStatement(sql);

        // Ejecutar la consulta y obtener el resultado
        ResultSet rs = ps.executeQuery();
        double saldoInicial = 0;
        if (rs.next()) {
            saldoInicial = rs.getDouble("monto");
        }

        // Cerrar recursos
        rs.close();
        ps.close();

        return saldoInicial;
    }

    //METODO DE INSERTAR EMPLEADOS/NUEVOS USUARIOS--
    public boolean insertarUsuarios(String nom_emple, String ape_emple, String ced_emple, String tele_emple, String usuario, String clave, int rol) {

        int resultado = 0;

        /*
            Abajo. Es simplemente una cadena que contiene la consulta de SQL. Esta consulta cuenta el número de 
            registros en la tabla reg_empleados donde
            el campo usuario coincide con el valor proporcionado como parámetro. 
            Igual ocurre con CC*/
        String sqlCheckUsername = "SELECT COUNT(*) FROM reg_empleados WHERE usuario = ?";
        String sqlCheckCC = "SELECT COUNT (*) FROM reg_empleados WHERE ced_emple = ?";

        String sql = "INSERT INTO reg_empleados (nom_emple, ape_emple, ced_emple, tele_emple, usuario, clave, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Eliminar los espacios en blanco al principio y al final de cada cadena
        nom_emple = nom_emple.trim();
        ape_emple = ape_emple.trim();
        ced_emple = ced_emple.trim();
        tele_emple = tele_emple.trim();
        usuario = usuario.trim();
        clave = clave.trim();

        try (Connection cn = con.getConnection(); PreparedStatement checarNom = cn.prepareStatement(sqlCheckUsername); PreparedStatement checarCedula = cn.prepareStatement(sqlCheckCC); PreparedStatement ps = cn.prepareStatement(sql)) {

            checarNom.setString(1, usuario);
            ResultSet rs = checarNom.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                /*Si es mayor a cero, significa que ya existe un usuario con ese nombre, por lo que devolverá false y no se agregará.*/
                JOptionPane.showMessageDialog(null, "¡Ese nombre de usuario ya está en uso! Elija otro.");
                return false;
            }

            checarCedula.setString(1, ced_emple);
            /*ResultSet*/ rs = checarCedula.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "ILEGAL: ¡Usted ya está registrado!");
                return false;
            }

            // Deshabilitar el modo de autocommit para utilizar transacciones
            cn.setAutoCommit(false);

            Empleados empleado = new Empleados();

            empleado.setNombre(nom_emple);
            empleado.setApellido(ape_emple);
            empleado.setCed(ced_emple);
            empleado.setTelefono(tele_emple);
            empleado.setUsuario(usuario);
            empleado.setClave(clave);
            empleado.setRol(rol);

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getCed());
            ps.setString(4, empleado.getTelefono());
            ps.setString(5, empleado.getUsuario());
            ps.setString(6, empleado.getClave());
            ps.setInt(7, empleado.getRol());

            resultado = ps.executeUpdate();

            // Realizar el commit de la transacción
            cn.commit();

        } catch (SQLException e) {
            System.out.println("¡Error al crear cuenta!   " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return false;
        }
        return resultado > 0;
    }

    //METODO PARA AUTENTICAR USUARIOS--
    public int autenticarUsuarios(String pUser, String pClave) {
        String claveEncriptada = hash.sha1(pClave);

        String sql = "SELECT usuario, clave, rol FROM reg_empleados WHERE usuario=? AND clave=?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, pUser);
            ps.setString(2, claveEncriptada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("rol");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    public int obtenerIdEmpleado(String usuario, String hora_cierre, String fecha_cierre) throws SQLException {
        int idEmpleado = 0; // Valor por defecto si no se encuentra el empleado

        String sql = "SELECT id_emple FROM reg_empleados WHERE usuario = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idEmpleado = rs.getInt("id_emple");

                    // Obtener el monto inicial de reg_saldo_inicial
                    double saldoInicio = 0.0;
                    String obtenerMontoSql = "SELECT monto FROM reg_saldo_inicial WHERE id_sald_ini = 1";
                    try (PreparedStatement psMonto = cn.prepareStatement(obtenerMontoSql)) {
                        cn.setAutoCommit(false);
                        ResultSet rsMonto = psMonto.executeQuery();
                        if (rsMonto.next()) {
                            saldoInicio = rsMonto.getDouble("monto");
                        }
                    }

                    String insertarSql = "INSERT INTO aud_cierre_caja (usuario, saldo_inicio, hora_cierre, fecha_cierre) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement psInsert = cn.prepareStatement(insertarSql)) {
                        psInsert.setInt(1, idEmpleado);
                        psInsert.setDouble(2, saldoInicio);
                        psInsert.setString(3, hora_cierre);
                        psInsert.setString(4, fecha_cierre);
                        psInsert.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener el ID del empleado: " + e.getMessage());
            }
            return idEmpleado;
        }
    }

    //MÉTODO PARA INSERTAR MESEROS--
    public boolean insertarMesero(String nom_emple, String ape_emple, String ced_emple, String tele_emple, String usuario, String clave, int rol) {

        int resultado = 0;
        String sqlCheckCC = "SELECT COUNT (*) FROM reg_empleados WHERE ced_emple = ?";

        String sql = "INSERT INTO reg_empleados (nom_emple, ape_emple, ced_emple, tele_emple, rol) VALUES (?, ?, ?, ?, 3)";

        nom_emple = nom_emple.trim();
        ape_emple = ape_emple.trim();
        ced_emple = ced_emple.trim();
        tele_emple = tele_emple.trim();

        try (Connection cn = con.getConnection(); PreparedStatement checarCedula = cn.prepareStatement(sqlCheckCC); PreparedStatement ps = cn.prepareStatement(sql)) {

            checarCedula.setString(1, ced_emple);
            ResultSet rs = checarCedula.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "ILEGAL: ¡Usted ya está registrado!");
                return false;
            }

            cn.setAutoCommit(false);

            Empleados empleado = new Empleados();

            empleado.setNombre(nom_emple);
            empleado.setApellido(ape_emple);
            empleado.setCed(ced_emple);
            empleado.setTelefono(tele_emple);

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getCed());
            ps.setString(4, empleado.getTelefono());

            resultado = ps.executeUpdate();

            // Realizar el commit de la transacción
            cn.commit();

        } catch (SQLException e) {
            System.out.println("¡Error al crear cuenta!   " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return false;
        }
        return resultado > 0;
    }

    //OBTENER NOMBRE DE MESERO
    public List<String> obtenerNombresMeseros() {

        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nom_emple FROM reg_empleados WHERE rol = 3"; // Suponiendo que el rol 3 corresponde a los meseros

        try (
                PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("nom_emple"));
            }

        } catch (SQLException e) {
        }
        return nombres;
    }

    //METODO PARA AGREGAR CLIENTE-- (No se alteró)
    public int agregarCliente(String nom_clie, String ape_clie, String ced_ruc, String direccion, String telef) {
        int resultado = 0;

        String sql = "INSERT INTO REG_CLIENTE (nom_clie, ape_clie, ced_ruc, direccion, telef) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = con.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            cn.setAutoCommit(false); // Deshabilitar el modo de autocommit para utilizar transacciones

            ps.setString(1, nom_clie);
            ps.setString(2, ape_clie);
            ps.setString(3, ced_ruc);
            ps.setString(4, direccion);
            ps.setString(5, telef);

            resultado = ps.executeUpdate();

            cn.commit(); // Realizar el commit de la transacción
        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    //MÉTODO PARA ELIMINAR CLIENTE
    public boolean eliminarCliente(String id_cliente) {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿De verdad desea borrar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM reg_cliente WHERE id_cliente = ?";
            try (Connection cn = con.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, id_cliente);

                int resultado = ps.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente.");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error al eliminar cliente: " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public Clientes unicoCliente(String id) {
        Clientes cliente = null;
        String sql = "SELECT * FROM reg_cliente WHERE id_cliente = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Clientes();
                    cliente.setId_cliente(rs.getInt(1)); // Asumiendo que el ID está en la primera columna
                    cliente.setNombre_clie(rs.getString(2));
                    cliente.setApe_clie(rs.getString(3));
                    cliente.setCed_ruc(rs.getString(4));
                    cliente.setDireccion_clie(rs.getString(5));
                    cliente.setTelef_clie(rs.getString(6));
                    // Si tienes más campos en tu tabla, asegúrate de establecerlos aquí
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente: " + e.getMessage());
        }
        return cliente;
    }

    //OBTENER CLIENTE
    public Clientes obtenerClientePorCedula(String cedula) {
        Clientes cliente = null;
        String sql = "SELECT * FROM reg_cliente WHERE ced_ruc = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                cn.setAutoCommit(false);
                if (rs.next()) {
                    cliente = new Clientes(); // Aquí asignamos el objeto cliente creado     
                    cliente.setNombre_clie(rs.getString(2));
                    cliente.setApe_clie(rs.getString(3));
                    cliente.setCed_ruc(rs.getString(4));
                    cliente.setDireccion_clie(rs.getString(5));
                    cliente.setTelef_clie(rs.getString(6));
                    // Si tienes más campos en tu tabla, asegúrate de establecerlos aquí
                }

            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente: " + e.getMessage());
        }
        return cliente;
    }

    //VERIFICAR CEDULAS
    public boolean verificarCeudulasSimilares(String cedula) {
        String sql = "SELECT COUNT(*) FROM reg_cliente WHERE ced_ruc = ?";
        int count = 0;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                cn.setAutoCommit(false);
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia del cliente: " + e.getMessage());
        }

        return count > 0;
    }

    public void actCliente(String nombre, String apellido, String direccion, String telefono, String cedRuc) {
        String sql = "UPDATE reg_cliente SET ";

        // Variable para mantener el número de campos a actualizar
        int numCamposActualizados = 0;
        java.util.ArrayList<String> valoresCampos = new java.util.ArrayList<>();
        if (!nombre.isEmpty()) {
            sql += "nom_clie = ?, ";
            valoresCampos.add(nombre);
            numCamposActualizados++;
        }
        if (!apellido.isEmpty()) {
            sql += "ape_clie = ?, ";
            valoresCampos.add(apellido);
            numCamposActualizados++;
        }
        if (!direccion.isEmpty()) {
            sql += "direccion = ?, ";
            valoresCampos.add(direccion);
            numCamposActualizados++;
        }
        if (!telefono.isEmpty()) {
            sql += "telef = ?, ";
            valoresCampos.add(telefono);
            numCamposActualizados++;
        }
        if (numCamposActualizados == 0) {
            JOptionPane.showMessageDialog(null, "No se proporcionó ningún campo para actualizar.");
            return;
        }

        sql = sql.substring(0, sql.length() - 2);
        sql += " WHERE ced_ruc = ?";

        try (
                PreparedStatement pstmt = cn.prepareStatement(sql);) {
            // Establecer los valores de los campos a actualizar
            for (int i = 0; i < valoresCampos.size(); i++) {
                pstmt.setString(i + 1, valoresCampos.get(i));
            }
            pstmt.setString(valoresCampos.size() + 1, cedRuc);

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Los datos se han actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún cliente con el número de cédula proporcionado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar Cliente: " + e.getMessage());
        }
    }

    //MÉTODO PARA LISTAR CLIENTES
    public List<Clientes> listar() {
        List<Clientes> datos = new ArrayList<>();
        String sql = "SELECT * FROM reg_cliente";
        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Clientes c = new Clientes();
                c.setId_cliente(rs.getInt(1));
                c.setNombre_clie(rs.getString(2));
                c.setApe_clie(rs.getString(3));
                c.setCed_ruc(rs.getString(4));
                c.setDireccion_clie(rs.getString(5));
                c.setTelef_clie(rs.getString(6));
                datos.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return datos;
    }

    //MÉTODO PARA LISTAR MESA
    public List<Mesa> listarAGGM() {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM reg_mesa";
        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet ro = ps.executeQuery()) {
            while (ro.next()) {
                Mesa m = new Mesa();
                m.setId_mesa(ro.getInt(1));
                m.setEstado(ro.getString(4));
                mesas.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar mesa: " + e.getMessage());
        }
        return mesas;
    }

    // MÉTODO PARA INGRESAR PEDIDO--
    public int ingresarPedido(String mesa, String mesero, String producto, String cantidad, String estado, String hora, String precio) {
        int resultado = 0;

        String sql = "INSERT INTO tmp_pedidos (mesa, mesero, producto, cantidad, estado, hora, precio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (mesa.isEmpty() || mesero.isEmpty() || producto.isEmpty() || cantidad.isEmpty() || estado.isEmpty() || hora.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡Por favor rellene los campos!");
            return resultado;
        }
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, mesa);
            ps.setString(2, mesero);
            ps.setString(3, producto);
            ps.setString(4, cantidad);
            ps.setString(5, estado);
            ps.setString(6, hora);
            ps.setString(7, precio);

            resultado = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al agregar pedido: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        if (resultado < 0) {
            JOptionPane.showMessageDialog(null, "Error al ingresar pedido");
        }
        return resultado;
    }

    // Método para restar la cantidad ingresada por el usuario de la tabla prod_platos
    public void restarCantidadProducto(String nombreProducto, String cantidad) {
        // Definir los PreparedStatement fuera del bloque try para cerrarlos adecuadamente en caso de excepción
        try (PreparedStatement selectStmt = cn.prepareStatement("SELECT cantidad FROM prod_platos WHERE nombre = ?"); PreparedStatement updateStmt = cn.prepareStatement("UPDATE prod_platos SET cantidad = ? WHERE nombre = ?")) {

            // Establecer el nombre del producto en la consulta SELECT
            selectStmt.setString(1, nombreProducto);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                String cantidadActualP = resultSet.getString("cantidad");
                int cantidadActual = Integer.parseInt(cantidadActualP);
                int cantidadIngresada = Integer.parseInt(cantidad);
                int nuevaCantidad = cantidadActual - cantidadIngresada;

                // Establecer los parámetros para la actualización en la consulta UPDATE
                updateStmt.setInt(1, nuevaCantidad);
                updateStmt.setString(2, nombreProducto);
                updateStmt.executeUpdate();
            } else {
                System.out.println("No se encontró el producto: " + nombreProducto);
            }
        } catch (SQLException e) {
            System.out.println("Error al restar cantidad del producto: " + e.getMessage());
            try {
                if (cn != null && !cn.getAutoCommit()) {
                    cn.rollback(); // Revertir la transacción en caso de error
                }
            } catch (SQLException ex) {
                System.out.println("Error al hacer roll: " + ex.getMessage());
            }
        }
    }

    // MÉTODO PARA REALIZAR UN PEDIDO--
    public int act(String id_pedidos) {
        int resultado = 0;
        String sql = "UPDATE tmp_pedidos SET estado = 'Listo' WHERE id_pedidos = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, id_pedidos);
            resultado = ps.executeUpdate();

            cn.commit(); // Realizar el commit de la transacción
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    public int obtenerCantidadDisponible(String producto, String categoria) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int cantidadDisponible = 0;

        try {
            // Consultar la cantidad disponible del producto según la categoría
            String sql;
            if (categoria.equals("Platos")) {
                sql = "SELECT cantidad FROM prod_platos WHERE nombre = ?";
            } else if (categoria.equals("Bebidas")) {
                sql = "SELECT cantidad FROM prod_bebidas WHERE nombre = ?";
            } else {
                throw new IllegalArgumentException("Categoría de producto no válida: " + categoria);
            }

            ps = cn.prepareStatement(sql);
            ps.setString(1, producto);
            rs = ps.executeQuery();

            // Obtener la cantidad disponible del resultado de la consulta
            if (rs.next()) {
                cantidadDisponible = rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return cantidadDisponible;
    }

    // MÉTODO PARA OBTENER LOS DATOS DEL PEDIDO--
    public List<Pedido> datosPedidos() {

        List<Pedido> datos = new ArrayList<>();
        String sql = "SELECT * FROM tmp_pedidos";

        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId_pedidos(rs.getInt(1));
                p.setMesa(rs.getString(2));
                p.setProducto(rs.getString(3));
                p.setCantidad(rs.getString(4));
                p.setEstado(rs.getString(5));
                p.setMesero(rs.getString(6));
                p.setHora(rs.getString(7));
                p.setPrecio(rs.getString(8));
                datos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos de pedidos: " + e.getMessage());
        }
        return datos;
    }

    //MÉTODO PARA LISTAR PEDIDO
    public List<Pedido> listarINGP() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM temp_pedidos";
        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet pi = ps.executeQuery()) {
            while (pi.next()) {
                Pedido p = new Pedido();
                p.setId_pedidos(pi.getInt(1));
                p.setMesa(pi.getString(2));
                p.setMesero(pi.getString(3));
                p.setProducto(pi.getString(4));
                p.setCantidad(pi.getString(5));
                p.setEstado(pi.getString(6));
                p.setHora(pi.getString(7));
                pedidos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al ingresar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    // MÉTODO PARA OCUPAR LA MESA--
    public int ocuparMesa(String id_mesa) {
        int resultado = 0;
        String sql = "UPDATE reg_mesa SET estado = 'OCUPADA' WHERE id_mesa = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, id_mesa);
            resultado = ps.executeUpdate();
            cn.commit(); // Realizar el commit de la transacción

        } catch (SQLException e) {
            System.out.println("Error al ocupar mesa: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    // MÉTODO PARA DESOCUPAR LA MESA--
    public int desocuparMesa(String id_mesa) {
        int resultado = 0;
        String sql = "UPDATE reg_mesa SET estado = 'DISPONIBLE' WHERE id_mesa = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, id_mesa);
            resultado = ps.executeUpdate();
            cn.commit(); // Realizar el commit de la transacción

        } catch (SQLException e) {
            System.out.println("Error al desocupar mesa: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    public int EliminaTmp(String id_mesa) throws SQLException {
        int result = 0;
        String sql = "DELETE FROM tmp_pedidos WHERE mesa = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, id_mesa);
            result = ps.executeUpdate();
        }
        return result;

    }

    public boolean consultarEstadoMesa(String mesa) {
        boolean mesaOcupada = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT estado FROM reg_mesa WHERE id_mesa = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, mesa);
            rs = ps.executeQuery();

            if (rs.next()) {
                String estado = rs.getString("estado");
                mesaOcupada = estado.equals("OCUPADA");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el estado de la mesa: " + e.getMessage());
        } finally {
            // Cerrar recursos en el bloque finally
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar ResultSet: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar PreparedStatement: " + ex.getMessage());
                }
            }
        }

        return mesaOcupada;
    }

    // Método para verificar si la mesa tiene pedidos asociados
    public boolean verificarPedidosMesa(String mesa) {
        String sql = "SELECT COUNT(*) FROM tmp_pedidos WHERE mesa = ?";
        boolean hasPedidos = false; // Variable para almacenar si hay pedidos en la mesa
        PreparedStatement ps = null; // Declaración del PreparedStatement fuera del bloque try

        try {
            ps = cn.prepareStatement(sql);
            cn.setAutoCommit(false);
            ps.setString(1, mesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    hasPedidos = count > 0;
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al verificar pedidos de la mesa: " + e.getMessage());
        } finally {
            // Cerrar conexiones y recursos en el bloque finally
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar el PreparedStatement: " + ex.getMessage());
            }
        }
        return hasPedidos;
    }

    public List<Pedido> consultarPedidosPorMesa(int idMesa) {
        List<Pedido> resultados = new ArrayList<>();
        String sql = "SELECT cantidad, producto,precio FROM tmp_pedidos WHERE mesa = ?";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            pst.setInt(1, idMesa);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.cantidad = rs.getString(1);
                p.producto = rs.getString(2);
                p.precio = rs.getString(3);
                resultados.add(p);
            }
            cn.commit();
        } catch (SQLException e) {
        }
        return resultados;
    }

    //DATOS DE MESA PARA TABLA--
    public List<Mesa> datosMesa() {
        List<Mesa> dataMesa = new ArrayList<>();
        String sql = "Select * FROM reg_mesa";
        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            cn.setAutoCommit(false);
            while (rs.next()) {
                Mesa m = new Mesa();
                m.setId_mesa(rs.getInt(1));
                m.setEstado(rs.getString(4));
                dataMesa.add(m);
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos de la mesa: " + e.getMessage());
        }
        return dataMesa;
    }

    // MÉTODO PARA AGREGAR PLATO
    public int agregarPlato(String nombre, String cantidad, String precio) {
        int resultado = 0;
        String sql = "INSERT INTO prod_platos (nombre, cantidad, precio) VALUES (?, ?, ?)";
        try (Connection cn = con.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false); // Deshabilitar el modo de autocommit para utilizar transacciones

            ps.setString(1, nombre);
            ps.setString(2, cantidad);
            ps.setString(3, precio);

            resultado = ps.executeUpdate();

            cn.commit(); // Realizar el commit de la transacción
        } catch (SQLException e) {
            System.out.println("Error al agregar plato: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    // MÉTODO AGREGAR BEBIDA
    public int agregarBebida(String nombre, String cantidad, String precio) {
        int resultado = 0;
        String sql = "INSERT INTO prod_bebidas (nombre, cantidad, precio) VALUES (?, ?, ?)";
        try (Connection cn = con.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false); // Deshabilitar el modo de autocommit para utilizar transacciones

            ps.setString(1, nombre);
            ps.setString(2, cantidad);
            ps.setString(3, precio);

            resultado = ps.executeUpdate();

            cn.commit(); // Realizar el commit de la transacción
        } catch (SQLException e) {
            System.out.println("Error al agregar plato: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    public int actualizarCantidadPlato(String nombre) {
        int resultado = 0;
        String sql = "SELECT cantidad FROM prod_platos WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar cantidad: " + e.getMessage());
        }

        return resultado;
    }

    public int actualizarCantidadBebida(String nombre) {
        int resultado = 0;
        String sql = "SELECT cantidad FROM prod_bebidas WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar cantidad: " + e.getMessage());
        }

        return resultado;
    }

    //MÉTODO ACTUALIZAR PLATO
    public int actualizarPlato(int cantidad, String nombre) {
        int resultado = 0;
        String sql = "UPDATE prod_platos SET cantidad = ? WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setInt(1, cantidad);
            ps.setString(2, nombre);

            resultado = ps.executeUpdate();
            cn.commit(); // Realizar el commit de la transacción

        } catch (SQLException e) {
            System.out.println("Error al actualizar plato: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    public int actualizarBebida(int cantidad, String nombre) {
        int resultado = 0;
        String sql = "UPDATE prod_bebidas SET cantidad = ? WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setInt(1, cantidad);
            ps.setString(2, nombre);

            resultado = ps.executeUpdate();
            cn.commit(); // Realizar el commit de la transacción

        } catch (SQLException e) {
            System.out.println("Error al actualizar bebida: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    //MÉTODO LISTAR PLATOS
    public List<Productos> listarPlatos() {

        List<Productos> platos = new ArrayList<>();
        String sql = "SELECT * FROM prod_platos";

        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Productos p = new Productos();
                p.setId_producto(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCantidad(rs.getString(3));
                p.setPrecio(rs.getString(4));
                platos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos de pedidos: " + e.getMessage());
        }
        return platos;
    }

    //MÉTODO LISTAR BEBIDAS
    public List<Productos> listarBebidas() {

        List<Productos> bebidas = new ArrayList<>();
        String sql = "SELECT * FROM prod_bebidas";

        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Productos p = new Productos();
                p.setId_producto(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCantidad(rs.getString(3));
                p.setPrecio(rs.getString(4));
                bebidas.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos de pedidos: " + e.getMessage());
        }
        return bebidas;
    }

    public int agregarProdEnt(String nombre, int cantidad, String fecha) {
        int resultado = 0;
        String sql = "INSERT INTO iv_prod_ent (nombre, cantent, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false); // Deshabilitar el modo de autocommit para utilizar transacciones

            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.setString(3, fecha);
            resultado = ps.executeUpdate();

            cn.commit(); // Realizar el commit de la transacción
        } catch (SQLException e) {
            System.out.println("Error al agregar el producto de entrada: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    public int agregarProdSal(String nombre, int cantidad, String fecha) {
        int resultado = 0;
        String sql = "INSERT INTO iv_prod_sal (nombre, cantsal, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false); // Deshabilitar el modo de autocommit para utilizar transacciones

            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.setString(3, fecha);
            resultado = ps.executeUpdate();

            cn.commit(); // Realizar el commit de la transacción
        } catch (SQLException e) {
            System.out.println("Error al agregar el producto de entrada: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;

    }

    public List<String> seleccionarFechasEnt(String nombre) {
        List<String> fechas = new ArrayList<>();
        String sql = "SELECT DISTINCT fecha FROM iv_prod_ent WHERE nombre = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre); // Establecer el valor del parámetro
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String fecha = rs.getString("fecha");
                    fechas.add(fecha);
                }

            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al obtener fechas: " + e.getMessage());
        }
        return fechas;
    }

    public List<String> seleccionarFechasSal(String nombre) {
        List<String> fechas = new ArrayList<>();
        String sql = "SELECT DISTINCT fecha FROM iv_prod_sal WHERE nombre = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre); // Establecer el valor del parámetro
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String fecha = rs.getString("fecha");
                    fechas.add(fecha);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al obtener fechas: " + e.getMessage());
        }
        return fechas;
    }

    public List<RegistrosProductos> listarProdEnt(String nombre, String fecha) {
        List<RegistrosProductos> prodEnt = new ArrayList<>();
        String sql = "SELECT * FROM iv_prod_ent WHERE nombre = ? AND fecha = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            ps.setString(2, fecha);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    RegistrosProductos rp = new RegistrosProductos();
                    rp.setId_producto(rs.getInt(1));
                    rp.setNombre(rs.getString(2));
                    rp.setCantidad(rs.getString(3));
                    rp.setFecha(rs.getString(4));
                    prodEnt.add(rp);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al listar los registros: " + e.getMessage());
        }
        return prodEnt;
    }

    public List<RegistrosProductos> listarProdSal(String nombre, String fecha) {
        List<RegistrosProductos> prodSal = new ArrayList<>();
        String sql = "SELECT * FROM iv_prod_sal WHERE nombre = ? AND fecha = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            ps.setString(2, fecha);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    RegistrosProductos rp = new RegistrosProductos();
                    rp.setId_producto(rs.getInt(1));
                    rp.setNombre(rs.getString(2));
                    rp.setCantidad(rs.getString(3));
                    rp.setFecha(rs.getString(4));
                    prodSal.add(rp);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al listar los registros: " + e.getMessage());
        }
        return prodSal;
    }

    public List<RegistrosProductos> listarUltimasEnt(String nombre) {
        List<RegistrosProductos> prodEnt = new ArrayList<>();
        String sql = "SELECT * FROM iv_prod_ent WHERE nombre = ? ORDER BY fecha DESC LIMIT 2";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistrosProductos rp = new RegistrosProductos();
                    rp.setId_producto(rs.getInt(1));
                    rp.setNombre(rs.getString(2));
                    rp.setCantidad(rs.getString(3));
                    rp.setFecha(rs.getString(4));
                    prodEnt.add(rp);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al listar los registros: " + e.getMessage());
        }
        return prodEnt;
    }

    public List<RegistrosProductos> listarUltimasSal(String nombre) {
        List<RegistrosProductos> prodSal = new ArrayList<>();
        String sql = "SELECT * FROM iv_prod_sal WHERE nombre = ? ORDER BY fecha DESC LIMIT 2";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistrosProductos rp = new RegistrosProductos();
                    rp.setId_producto(rs.getInt(1));
                    rp.setNombre(rs.getString(2));
                    rp.setCantidad(rs.getString(3));
                    rp.setFecha(rs.getString(4));
                    prodSal.add(rp);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al listar los registros: " + e.getMessage());
        }
        return prodSal;
    }

    public int consultarPrecioPlato(String nombre) {
        int resultado = 0;
        String sql = "SELECT precio FROM prod_platos WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar precio: " + e.getMessage());
        }

        return resultado;
    }

    public int consultarPrecioBebida(String nombre) {
        int resultado = 0;
        String sql = "SELECT precio FROM prod_bebidas WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar precio: " + e.getMessage());
        }

        return resultado;
    }

    public int actualizarIvTemporal(int cantent, int cantsal, String fecha, String nombre) {
        int resultado = 0;
        String sql = "UPDATE iv_temp SET cantent = ?, cantsal = ?, fecha = ? WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setInt(1, cantent);
            ps.setInt(2, cantsal);
            ps.setString(3, fecha);
            ps.setString(4, nombre);

            resultado = ps.executeUpdate();
            cn.commit(); // Realizar el commit de la transacción

        } catch (SQLException e) {
            System.out.println("Error al actualizar inventario temporal: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        }
        return resultado;
    }

    public int actualizarCantidadIvEnt(String nombre) {
        int resultado = 0;
        String sql = "SELECT cantent FROM iv_temp WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar cantidad: " + e.getMessage());
        }

        return resultado;
    }

    public int actualizarCantidadIvSal(String nombre) {
        int resultado = 0;
        String sql = "SELECT cantsal FROM iv_temp WHERE nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar cantidad: " + e.getMessage());
        }

        return resultado;
    }

    public String seleccionarProductoTmp(int idMesa) {
        String resultado = null;
        String sql = "SELECT producto FROM tmp_pedidos WHERE mesa = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getString("producto");
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar producto: " + e.getMessage());
        }
        return resultado;
    }

    public String seleccionarCantidadTmp(int idMesa) {
        String resultado = null;
        String sql = "SELECT cantidad FROM tmp_pedidos WHERE mesa = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getString("cantidad");
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar producto: " + e.getMessage());
        }

        return resultado;
    }

    public String seleccionarPrecioTmp(int idMesa) {
        String resultado = null;
        String sql = "SELECT precio FROM tmp_pedidos WHERE mesa = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getString("precio");
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar producto: " + e.getMessage());
        }

        return resultado;
    }

    public int agregarFactura(String producto, String cantidad, String total, String cedula, String mesero, String fecha, String hora) {
        int resultado = 0;
        String sql = "INSERT INTO fact_detalle (producto, cant_prod, total,id_clie,Nom_Mesero,fecha_fact,hora_fact) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, producto);
            ps.setString(2, cantidad);
            ps.setString(3, total);
            ps.setString(4, cedula);
            ps.setString(5, mesero);
            ps.setString(6, fecha);
            ps.setString(7, hora);
            resultado = ps.executeUpdate();
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al agregar la factura: " + e.getMessage());
            try {
                cn.rollback(); // Revertir la transacción en caso de excepción
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                cn.setAutoCommit(true); // Restaurar el autocommit a su estado original
            } catch (SQLException ex) {
                System.out.println("Error al restaurar el autocommit: " + ex.getMessage());
            }
        }
        return resultado;
    }

    public int consultMeseroXnom(String mesero) {
        int resultado = 0;
        String sql = "SELECT id_emple FROM reg_empleados WHERE nom_emple = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, mesero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }

            cn.commit();
        } catch (SQLException e) {
            System.out.println("Err al consultar el id del mesero: " + e.getMessage());
        }

        return resultado;
    }

    public int consultarIdCajero(String usuario) {
        int resultado = 0;
        String sql = "SELECT id_emple FROM reg_empleados WHERE usuario = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar el id del cajero: " + e.getMessage());
        }
        return resultado;

    }

    public int consultarIdCliente(String cedula) {
        int resultado = 0;
        String sql = "SELECT id_cliente FROM reg_cliente WHERE ced_ruc = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al consultar el id del cajero: " + e.getMessage());
        }

        return resultado;
    }

    public String obtenerMeseroPorItem(String item) {
        String mesero = null;
        String sql = "SELECT mesero FROM tmp_pedidos WHERE mesa = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            ps.setString(1, item);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mesero = rs.getString("mesero");
                }
            }
            cn.commit();
        } catch (SQLException e) {
            System.out.println("Error al obtener el mesero: " + e.getMessage());
        }

        return mesero;
    }
    //Metodo hash para introducir la consulta de la tabla fact_detalle

    private List<Map<String, Object>> createResultSetFromPedidos(List<Pedido> pedidos) {
        List<Map<String, Object>> resultSet = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            Map<String, Object> row = new HashMap<>();
            row.put("producto", pedido.getProducto());
            row.put("cant_prod", pedido.getCantidad());
            row.put("precio", pedido.getPrecio());
            // Añadir otras columnas según sea necesario
            resultSet.add(row);
        }

        return resultSet;
    }

    public List<Map<String, Object>> obtenerDatosPorMesa(String mesa) throws SQLException {
        List<Pedido> pedidos = consultarPedidosPorMesa(Integer.parseInt(mesa));
        return createResultSetFromPedidos(pedidos);
    }

    //Metodo hash para introducir la consulta de la tabla fact_detalle
    public HashMap<String, ResultSet> obtenerDatos(String cedula) throws SQLException {
        HashMap<String, ResultSet> resultados = new HashMap<>();
        PreparedStatement statementFactDetalle = null;
        ResultSet resultSetFactDetalle = null;
        String queryFactDetalle = "SELECT id_numero_fact, total, id_clie, tipo_pago, Nom_Mesero, descuento, iva, fecha_fact, hora_fact FROM fact_detalle WHERE id_clie = ? ORDER BY fecha_fact DESC, hora_fact DESC";
        try {
            statementFactDetalle = cn.prepareStatement(queryFactDetalle);
            statementFactDetalle.setString(1, cedula);
            resultSetFactDetalle = statementFactDetalle.executeQuery();

            resultados.put("fact_detalle", resultSetFactDetalle);
        } catch (SQLException e) {
            // Manejar la excepción si ocurre
            throw e;
        }

        return resultados;
    }

    //Metodo para mostrar en pantalla los datos consultados y localizados en el hash
    public void mostrarEnTextArea(List<Map<String, Object>> datosPedidos, ResultSet resultSetFactura, JTextArea textArea) {
        StringBuilder facturaText = new StringBuilder();
        double total = 0.0; // Variable to store the total initialized to 0.0

        facturaText.append("╔═════════════════════════════╗\n");
        facturaText.append("                                              \n");
        facturaText.append("                              FACTURA SUNSET                     \n");
        facturaText.append("                                        \\(◦'⌣'◦)/ \n");
        facturaText.append("╚═════════════════════════════╝\n\n");

        try {
            facturaText.append("  Número de Factura:  ").append(resultSetFactura.getString("id_numero_fact")).append("\n");
            facturaText.append("  Mesero:             ").append(resultSetFactura.getString("Nom_Mesero")).append("\n");
            facturaText.append("  Cliente:            ").append(resultSetFactura.getInt("id_clie")).append("\n\n");

            Map<String, Item> productMap = new HashMap<>();
            for (Map<String, Object> pedido : datosPedidos) {
                String producto = (String) pedido.get("producto");
                int cantidad = Integer.parseInt(pedido.get("cant_prod").toString());
                double precio = Double.parseDouble(pedido.get("precio").toString());

                Item item = productMap.get(producto);
                if (item == null) {
                    item = new Item(producto, cantidad, precio);
                } else {
                    item.cantidad += cantidad;
                    item.totalPrice += precio;
                }
                productMap.put(producto, item);
            }

            facturaText.append("  Productos:\n");
            facturaText.append("  -------------------------------------------------\n");
            facturaText.append("  | Producto         | Cantidad     | Precio       \n");
            facturaText.append("  -------------------------------------------------\n");
            for (Item item : productMap.values()) {
                facturaText.append("   ")
                        .append(String.format("%-20s", item.producto))
                        .append("  ")
                        .append(String.format(" %-20s", item.cantidad))
                        .append("  ")
                        .append(String.format("%-20s", String.format("$%.2f", item.totalPrice)))
                        .append(" \n");
                total += item.totalPrice;
            }
            facturaText.append("  -------------------------------------------------\n\n");            
            facturaText.append("  Total:               ").append(String.format("$%.2f", total)).append("          \n"); // Mostrar el total calculado
            facturaText.append("  Tipo de Pago:        ").append(resultSetFactura.getString("tipo_pago")).append("                  \n");
            facturaText.append("  Fecha:                ").append(resultSetFactura.getString("fecha_fact")).append("          \n");
            facturaText.append("  Hora:                 ").append(resultSetFactura.getString("hora_fact")).append("          \n");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar la factura: " + ex.getMessage());
        } finally {
            facturaText.append("╔═════════════════════════════╗\n");
            facturaText.append("                                            \n");
            facturaText.append("                         ¡Gracias por su preferencia!         \n");
            facturaText.append("                                             \n");
            facturaText.append("╚═════════════════════════════╝\n");

            textArea.setText(facturaText.toString());

            try {
                if (resultSetFactura != null) {
                    resultSetFactura.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try {
                cn.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
