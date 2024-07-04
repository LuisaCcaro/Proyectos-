package Controlador;
import Modelo.Clientes;
import Modelo.Productos;
import Modelo.Pedido;
import Modelo.CerrarCaja;
import Modelo.MetodosEmpleados;
import Modelo.ExportarExcel;
import Modelo.UtilidadesTablas;
import Modelo.RegistrosProductos;
import Modelo.Mesa;
import Modelo.UsuarioActual;
import Vista.RegistrarCliente;
import Vista.Inicio;
import Vista.GenerarFactura;
import Vista.IngresarPedido;
import Vista.ConsultarInventarioo;
import Vista.Menu_Admin;
import Vista.ingresarMesaa;
import Vista.Menu_Cajero;
import Vista.AgregarProductoo;
import Vista.CrearCuenta;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener, KeyListener {

    MetodosEmpleados metem = new MetodosEmpleados();
    Inicio ini = new Inicio();
    Menu_Admin adm = new Menu_Admin(ini);
    Menu_Cajero caj = new Menu_Cajero(ini);
    Clientes c = new Clientes();
    RegistrarCliente reg = new RegistrarCliente();
    IngresarPedido IngP = new IngresarPedido();
    AgregarProductoo prod = new AgregarProductoo();
    DefaultTableModel modelo = new DefaultTableModel();
    ingresarMesaa ingM = new ingresarMesaa();
    ConsultarInventarioo consul = new ConsultarInventarioo();
    GenerarFactura g = new GenerarFactura();
    CrearCuenta crearc = new CrearCuenta();
    

    public Controlador(Inicio ini, Menu_Admin adm, Menu_Cajero caj) {
        this.ini = ini;

        this.adm = adm;
        this.caj = caj;

        this.ini.InputPsswordUser.addKeyListener(this);
        this.caj.btnC.addActionListener(this);
        this.adm.btnA.addActionListener(this);
        this.ini.btnLogin.addActionListener(this);
        this.reg.btnGuardar.addActionListener(this);
        ImagenI();
    }

    public Controlador() {
    }

    public void autenticarUsuarios(String pUser, String pClave) {
        int rol = metem.autenticarUsuarios(pUser, pClave);
        switch (rol) {
            case 1:
                ini.setVisible(false);
                adm.setVisible(true);
                adm.setLocationRelativeTo(null);
                //Establecer el tamaño de la ventana a la resolución de la pantalla
                adm.setSize(Toolkit.getDefaultToolkit().getScreenSize());

                //Establecer el estado de la ventana en pantalla completa
                adm.setExtendedState(JFrame.MAXIMIZED_BOTH);
                break;

            case 2:
                ini.setVisible(false);
                caj.setVisible(true);
                caj.setLocationRelativeTo(null);
                //Establecer el tamaño de la ventana a la resolución de la pantalla
                caj.setSize(Toolkit.getDefaultToolkit().getScreenSize());

                //Establecer el estado de la ventana en pantalla completa
                caj.setExtendedState(JFrame.MAXIMIZED_BOTH);
                break;

            default:
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Ha ocurrido un error", JOptionPane.WARNING_MESSAGE);
                ini.InputUser.requestFocus();
                break;
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == ini.btnLogin) {
            iniciarSesion();
            //caj.C_content.removeAll(); Brayan haz esto publico :(
            
        }else if (e.getSource() == caj.btnC) {
            
            try {
                cerrarSesionCaj();
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void iniciarSesion(){
            caj.C_content.removeAll();
            adm.M_content.removeAll();
            String pUser = ini.InputUser.getText();
            String pClave = new String(ini.InputPsswordUser.getPassword());
            autenticarUsuarios(pUser, pClave);
            UsuarioActual.setNombreUsuario(pUser);
            // Obtén el ID del usuario
            try {
                int idUsuario = metem.obtenerIdUsuario(pUser); // Obtener el ID del usuario de la base de datos
                UsuarioActual.setIdUser(idUsuario); // Configurar el ID del usuario en la clase de utilidad
                
                // Obtener id en operaciones necesarias
                String nombreUsuario = metem.obtenerNom(pUser); // Obtener el nombre de usuario
                UsuarioActual.setNombreUsuario(nombreUsuario); 
                adm.nomEmple.setText("Hola, " + UsuarioActual.getNombreUsuario());
                caj.nomEmple.setText("Hola, " + UsuarioActual.getNombreUsuario());
                
                } catch (SQLException ex) {
                    System.out.println("Error al obtener el ID o el nombre del usuario: " + ex.getMessage());
                    ex.printStackTrace(); // Imprimir el seguimiento de la pila del error para depuración
                }
 }
    
    private boolean cerrarSesionCaj() throws SQLException{
        int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            
            String fechita = metem.mostrarfecha();
            String hora = metem.mostrarhora();

            // Obtener información del usuario y la entrada
            int usuario = UsuarioActual.getIdUser();
            String Usuario = String.valueOf(usuario);

            // Calcular el valor final del efectivo
            double saldoInicial = metem.obtenerSaldoInicial();
            double entrada = CerrarCaja.entrada();
            double caja = CerrarCaja.cajaYvalorActual();
            double valorActual = caja;

            try {
                metem.obtenerIdEmpleado(Usuario, hora, fechita);

                // Insertar cierre de caja
                metem.insertarCierreCaja(Usuario, saldoInicial, entrada, caja, valorActual);

                CerrarCaja.cerrarSesion();
                caj.setVisible(false);
                ini.setVisible(false);
                
                ini.InputUser.setText("");
                ini.InputPsswordUser.setText("");
                
                ini.setVisible(true);
                
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error al registrar cierre de caja: " + ex.getMessage());
                return false; // Devolver false si hay un error
            }

            return true; // Devolver true si todas las operaciones son exitosas
            
        } else {
            return false; // Devolver false si el usuario cancela el cierre de sesión
        }
    }
    

    public void cargarConsumo(int idMesa, JTable tablaConsumo, String mesa, String cedula, String item) {
        boolean mesaPedido = metem.verificarPedidosMesa(mesa);
       
        if (mesaPedido) {
            List<Pedido> pedidos = metem.consultarPedidosPorMesa(idMesa);

            // Mostrar los resultados en la tabla
            DefaultTableModel model = (DefaultTableModel) tablaConsumo.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
            UtilidadesTablas.tablaNormal(tablaConsumo);
            for (Pedido pedido : pedidos) {
                Object[] fila = new Object[]{
                    pedido.getCantidad(),
                    pedido.getProducto(),
                    pedido.getPrecio()
                };
                model.addRow(fila);

            }
            String producto = metem.seleccionarProductoTmp(idMesa);
            String cantidad = metem.seleccionarCantidadTmp(idMesa);
            String precio = metem.seleccionarPrecioTmp(idMesa);
            String mesero = metem.obtenerMeseroPorItem(item);
            String hora = metem.mostrarhora();
            String fecha = metem.mostrarfecha();
            metem.agregarFactura(producto, cantidad, precio, cedula, mesero, fecha, hora);
        } else {
            JOptionPane.showMessageDialog(null, "La mesa no está relacionada con ningun producto. Por favor, elija otra mesa.", "Mesa sin productos", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No es necesario implementar este método
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Verificar si la tecla presionada es Enter (código 10)
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String pUser = ini.InputUser.getText();
            String pClave = new String(ini.InputPsswordUser.getPassword());
            autenticarUsuarios(pUser, pClave);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No es necesario implementar este método
    }

    public void listar(JTable tablacliente) {
        modelo = (DefaultTableModel) tablacliente.getModel();
        //Eliminar todas las filas existentes
        modelo.setRowCount(0);

        List<Clientes> lista = metem.listar();
        Object[] objeto = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_cliente();
            objeto[1] = lista.get(i).getNombre_clie();
            objeto[2] = lista.get(i).getApe_clie();
            objeto[3] = lista.get(i).getCed_ruc();
            objeto[4] = lista.get(i).getDireccion_clie();
            objeto[5] = lista.get(i).getTelef_clie();
            modelo.addRow(objeto);

        }

        reg.tablacliente.setModel(modelo);
    }

    public void listarAGGM(JTable tblMesa) {
        modelo = (DefaultTableModel) tblMesa.getModel();
        //Eliminar todas las filas existentes
        modelo.setRowCount(0);

        List<Mesa> lista = metem.listarAGGM();
        Object[] objeto = new Object[2];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_mesa();
            objeto[1] = lista.get(i).getEstado();
            modelo.addRow(objeto);

        }

        ingM.tblMesa.setModel(modelo);
    }

    public void datosPedidos(JTable tablePedido) {
        modelo = (DefaultTableModel) tablePedido.getModel();
        //Eliminar todas las filas existentes
        modelo.setRowCount(0);

        List<Pedido> lista = metem.datosPedidos();
        Object[] objeto = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_pedidos();
            objeto[1] = lista.get(i).getMesa();
            objeto[2] = lista.get(i).getProducto();
            objeto[3] = lista.get(i).getCantidad();
            objeto[4] = lista.get(i).getEstado();
            objeto[5] = lista.get(i).getMesero();
            objeto[6] = lista.get(i).getHora();
            modelo.addRow(objeto);

        }

        IngP.tablePedido.setModel(modelo);
    }

    public void listarPlatos(JTable TablaProducto) {
        modelo = (DefaultTableModel) TablaProducto.getModel();
        //Eliminar todas las filas existentes
        modelo.setRowCount(0);
        List<Productos> lista = metem.listarPlatos();
        Object[] objeto = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_producto();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getCantidad();
            objeto[3] = lista.get(i).getPrecio();
            modelo.addRow(objeto);

        }

        prod.TablaProducto.setModel(modelo);
    }

    public void listarborradoCliente(JTable tablaclienteBorrar, Clientes cliente) {
        DefaultTableModel modelo = (DefaultTableModel) tablaclienteBorrar.getModel();
        modelo.setRowCount(0); // Eliminar todas las filas existentes
        if (cliente != null) {
            Object[] objeto = new Object[6];
            objeto[0] = cliente.getId_cliente();
            objeto[1] = cliente.getNombre_clie();
            objeto[2] = cliente.getApe_clie();
            objeto[3] = cliente.getCed_ruc();
            objeto[4] = cliente.getDireccion_clie();
            objeto[5] = cliente.getTelef_clie();
            modelo.addRow(objeto);
        } else {
            System.out.println("Cliente no encontrado.");
        }
        tablaclienteBorrar.setModel(modelo);
    }

    public void listarBebidas(JTable TablaProducto) {
        modelo = (DefaultTableModel) TablaProducto.getModel();
        //Eliminar todas las filas existentes
        modelo.setRowCount(0);
        List<Productos> lista = metem.listarBebidas();
        Object[] objeto = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_producto();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getCantidad();
            objeto[3] = lista.get(i).getPrecio();
            modelo.addRow(objeto);

        }

        prod.TablaProducto.setModel(modelo);
    }

    public void listarMesas(JTable tblMesa) {
        modelo = (DefaultTableModel) tblMesa.getModel();
        modelo.setRowCount(0);
        List<Mesa> lista = metem.datosMesa();
        Object[] objeto = new Object[2];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_mesa();
            objeto[1] = lista.get(i).getEstado();
            modelo.addRow(objeto);
        }

        ingM.tblMesa.setModel(modelo);
    }

    /*Método para colocarle icon a la imagen*/
    public void ImagenI() {
        try {
            Image img = ImageIO.read(new File("Icono.png"));
            ini.setIconImage(img);
            adm.setIconImage(img);
            caj.setIconImage(img);

            ini.setTitle("Sunset");
            adm.setTitle("Menú Administrativo");
            caj.setTitle("Menú Cajero");
        } catch (IOException e) {
            System.out.println("Upsi...");
        }
    }

    public void listarProdEnt(JTable tblInventario, String nombre, String fecha) {
        modelo = (DefaultTableModel) tblInventario.getModel();
        //Eliminar todas las filas existentes
        //modelo.setRowCount(0);
        List<RegistrosProductos> lista = metem.listarProdEnt(nombre, fecha);
        Object[] objeto = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_producto();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getCantidad();
            objeto[3] = 0;
            objeto[4] = lista.get(i).getFecha();
            
            modelo.addRow(objeto);

        }

        consul.tablainv.setModel(modelo);
    }

    public void listarProdSal(JTable tblInventario, String nombre, String fecha) {

        modelo = (DefaultTableModel) tblInventario.getModel();
        //Eliminar todas las filas existentes
        //modelo.setRowCount(0);
        List<RegistrosProductos> lista = metem.listarProdSal(nombre, fecha);
        Object[] objeto = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_producto();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = 0;
            objeto[3] = lista.get(i).getCantidad();
            objeto[4] = lista.get(i).getFecha();
            modelo.addRow(objeto);

        }

        consul.tablainv.setModel(modelo);
    }

    public void listarUltimasEnt(JTable tblInventario, String nombre) {

        modelo = (DefaultTableModel) tblInventario.getModel();
        //Eliminar todas las filas existentes
        //modelo.setRowCount(0);
        List<RegistrosProductos> lista = metem.listarUltimasEnt(nombre);
        Object[] objeto = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_producto();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getCantidad();
            objeto[3] = 0;
            objeto[4] = lista.get(i).getFecha();
            modelo.addRow(objeto);

        }

        consul.tablainv.setModel(modelo);
    }

    public void listarUltimasSal(JTable tblInventario, String nombre) {
        modelo = (DefaultTableModel) tblInventario.getModel();
        //Eliminar todas las filas existentes
        //modelo.setRowCount(0);
        List<RegistrosProductos> lista = metem.listarUltimasSal(nombre);
        Object[] objeto = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId_producto();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = 0;
            objeto[3] = lista.get(i).getCantidad();
            objeto[4] = lista.get(i).getFecha();
            modelo.addRow(objeto);

        }

        consul.tablainv.setModel(modelo);
    }

    public void mesaLibre(String mesaNu, JTable tblMesa) {

        boolean mesa = metem.consultarEstadoMesa(mesaNu);

        if (mesaNu != null && !mesaNu.equals("Seleccione Mesa...")) {
            int resultado = metem.desocuparMesa(mesaNu);
            if (!mesa) { // Mesa ya libre

                JOptionPane.showMessageDialog(null, "La mesa No." + mesaNu + " ya está desocupada. Por favor, elija otra mesa.");

            } else if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "¡La mesa se ha actualizado correctamente!");
                UtilidadesTablas.tablaNormal(tblMesa);
                listarMesas(tblMesa);
                ingM.boxMesas.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(null, "Ups, error al actualizar la mesa No." + mesaNu);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe de insertar una mesa...");
        }
    }

    public void mesaOcupada(String mesaNu, JTable tblMesa) {

        boolean mesa = metem.consultarEstadoMesa(mesaNu);

        if (mesaNu != null && !mesaNu.equals("Seleccione Mesa...")) {
            int resultado = metem.ocuparMesa(mesaNu);

            // Manejar el resultado para la operación
            if (mesa) { // Mesa ya ocupada

                JOptionPane.showMessageDialog(null, "La mesa No." + mesaNu + " ya está ocupada. Por favor, elija otra mesa.");

            } else if (resultado > 0) {
                // Mesa ocupada correctamente
                JOptionPane.showMessageDialog(null, "La mesa No." + mesaNu + " se ha ocupado correctamente");
                Controlador c = new Controlador();
                UtilidadesTablas.tablaNormal(tblMesa);
                c.listarMesas(tblMesa);

            } else {
                JOptionPane.showMessageDialog(null, "Ups, Ha ocurrido un error al ocupar la mesa No." + mesaNu);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe de insertar una mesa...");
        }
    }

    public void ingresarPedido(String mesa, String mesero, String producto, String cantidad, String categoria, JTable tablePedido) {

        boolean mesaOcupada = metem.consultarEstadoMesa(mesa); // Esta función debe retornar true si la mesa está ocupada, false si está libre
        // Obtener la cantidad de productos disponibles en la base de datos
        int cantidadDisponible = metem.obtenerCantidadDisponible(producto, categoria); // Implementa este método en MetodosEmpleados para obtener la cantidad disponible

        // Convertir la cantidad ingresada a entero
        int cantidadInt = Integer.parseInt(cantidad);

        if (!mesaOcupada) {
            // Si la mesa está libre, mostrar un mensaje y salir del método
            JOptionPane.showMessageDialog(null, "Debe seleccionar una mesa antes de ingresar un pedido, escoja una mesa e intente de nuevo.", "Mesa No Seleccionada", JOptionPane.WARNING_MESSAGE);
            return;

        } else if (cantidadInt <= 0 || cantidadInt > cantidadDisponible) {
            JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida o excede la cantidad disponible del producto.", "Cantidad No Válida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String hora = metem.mostrarhora();
        String fecha = metem.mostrarfecha();

        if (categoria.equals("Platos")) {

            int cantidadInicialPlato = metem.actualizarCantidadPlato(producto);
            int cantidadTotalPlato = cantidadInicialPlato - cantidadInt;
            int plato = metem.actualizarPlato(cantidadTotalPlato, producto);

        } else if (categoria.equals("Bebidas")) {

            int cantidadInicialBebida = metem.actualizarCantidadBebida(producto);
            int cantidadTotalBebida = cantidadInicialBebida - cantidadInt;
            int bebida = metem.actualizarBebida(cantidadTotalBebida, producto);

        }

        //Ingresar el pedido
        int cantidadIngrediente = Integer.parseInt(cantidad) * 100;

        if (producto.equals("Sushi")) {

            String[] ingredientes = {"arroz", "salmon", "alga nori", "aguacate"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Onigiri")) {

            String[] ingredientes = {"arroz", "salmon", "alga nori"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);

                }
            }

        } else if (producto.equals("Tonkatsu")) {

            String[] ingredientes = {"cerdo", "harina de trigo", "pan"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {
                for (String ingrediente : ingredientes) {
                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Tempura")) {

            String[] ingredientes = {"camarones", "harina de trigo"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {
                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Yakitori")) {

            String[] ingredientes = {"pollo", "cebolla", "arroz"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {
                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Okonomiyaki")) {

            String[] ingredientes = {"harina de trigo", "repollo"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {
                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Oyakodon")) {

            String[] ingredientes = {"pollo", "cebolla", "arroz"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Katsudon")) {

            String[] ingredientes = {"cerdo", "cebolla", "arroz"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {
                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Nikujaga")) {

            String[] ingredientes = {"cerdo", "papa", "cebolla", "zanahoria"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {
                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }

        } else if (producto.equals("Karaage")) {

            String[] ingredientes = {"pollo", "harina de trigo"};

            int precioPlato = metem.consultarPrecioPlato(producto);
            int precioTotal = precioPlato * cantidadInt;
            String precioPlatoFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioPlatoFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            if (resultado > 0) {

                for (String ingrediente : ingredientes) {
                    int resultadoIngrediente = metem.agregarProdSal(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantSal + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantEnt, cantidadTotal, fecha, ingrediente);
                }
            }
        } else if (producto.equals("Sake")) {

            int precioBebida = metem.consultarPrecioBebida(producto);
            int precioTotal = precioBebida * cantidadInt;
            String precioBebidaFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioBebidaFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            int resultadoIngrediente = metem.agregarProdEnt("Sake", cantidadIngrediente, fecha);
            int cantEnt = metem.actualizarCantidadIvEnt("Sake");
            int cantSal = metem.actualizarCantidadIvSal("Sake");
            int cantidadTotal = cantEnt + cantidadIngrediente;
            int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Sake");

        } else if (producto.equals("Amazake")) {

            int precioBebida = metem.consultarPrecioBebida(producto);
            int precioTotal = precioBebida * cantidadInt;
            String precioBebidaFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioBebidaFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            int resultadoIngrediente = metem.agregarProdEnt("Amazake", cantidadIngrediente, fecha);
            int cantEnt = metem.actualizarCantidadIvEnt("Amazake");
            int cantSal = metem.actualizarCantidadIvSal("Amazake");
            int cantidadTotal = cantEnt + cantidadIngrediente;
            int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Amazake");

        } else if (producto.equals("Ramune")) {

            int precioBebida = metem.consultarPrecioBebida(producto);
            int precioTotal = precioBebida * cantidadInt;
            String precioBebidaFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioBebidaFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            int resultadoIngrediente = metem.agregarProdEnt("Ramune", cantidadIngrediente, fecha);
            int cantEnt = metem.actualizarCantidadIvEnt("Ramune");
            int cantSal = metem.actualizarCantidadIvSal("Ramune");
            int cantidadTotal = cantEnt + cantidadIngrediente;
            int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Ramune");

        } else if (producto.equals("Agua")) {

            int precioBebida = metem.consultarPrecioBebida(producto);
            int precioTotal = precioBebida * cantidadInt;
            String precioBebidaFinal = Integer.toString(precioTotal);
            int resultado = metem.ingresarPedido(mesa, mesero, producto, cantidad, "Pendiente", hora, precioBebidaFinal);
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
            datosPedidos(tablePedido);

            int resultadoIngrediente = metem.agregarProdEnt("Ramune", cantidadIngrediente, fecha);
            int cantEnt = metem.actualizarCantidadIvEnt("Ramune");
            int cantSal = metem.actualizarCantidadIvSal("Ramune");
            int cantidadTotal = cantEnt + cantidadIngrediente;
            int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Ramune");

        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el pedido correctamente");
        }
    }

    public void cargarInfoCliente(String cedula, JTable tablafactu) {

        Clientes cliente = metem.obtenerClientePorCedula(cedula);

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor,asegurese de llenar el campo antes de buscar", "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {
            if (cliente != null) {
                UtilidadesTablas.tablaNormal(tablafactu);
                DefaultTableModel modelo = (DefaultTableModel) tablafactu.getModel();
                modelo.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
                Object[] objeto = new Object[5];

                objeto[0] = cliente.getNombre_clie();
                objeto[1] = cliente.getApe_clie();
                objeto[2] = cliente.getCed_ruc();
                objeto[3] = cliente.getDireccion_clie();
                objeto[4] = cliente.getTelef_clie();
                modelo.addRow(objeto);
            } else {
                // Manejar el caso en que no se encuentra ningún cliente con la cédula ingresada
                JOptionPane.showMessageDialog(null, "No se encontró ningún cliente con la cédula ingresada", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // Método para cargar el consumo para una mesa seleccionada

    public void actualizarYagregarProducto(String categoria, String producto, String cantidad, JTable TablaProducto, JTextField InputCantidad, JTextField productoPrecio) {

        if (categoria.equals("Elija una categoría...") || producto.equals("Elija el producto...") || cantidad.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos antes de guardar.");

        } else {

            int cantidadInt = Integer.parseInt(cantidad);
            int cantidadIngrediente = cantidadInt * 100;
            String fecha = metem.mostrarfecha();
            int precio = categoria.equals("Platos") ? metem.consultarPrecioPlato(producto) : metem.consultarPrecioBebida(producto);
            String precioFinal = Integer.toString(precio);

            if (categoria.equals("Platos") || categoria.equals("Bebidas")) {
                int cantidadInicial = categoria.equals("Platos") ? metem.actualizarCantidadPlato(producto) : metem.actualizarCantidadBebida(producto);
                int cantidadTotal = cantidadInicial + cantidadInt;
                int resultado = categoria.equals("Platos") ? metem.actualizarPlato(cantidadTotal, producto) : metem.actualizarBebida(cantidadTotal, producto);
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
                    UtilidadesTablas.tablaNormal(TablaProducto);
                    if (categoria.equals("Platos")) {
                        listarPlatos(TablaProducto);
                    } else {
                        listarBebidas(TablaProducto);
                    }
                    InputCantidad.setText("");
                    productoPrecio.setText(precioFinal);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar producto");
                }
            }

            //Ingresar el pedido
            if (producto.equals("Sushi")) {

                String[] ingredientes = {"arroz", "salmon", "alga nori", "aguacate"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Onigiri")) {

                String[] ingredientes = {"arroz", "salmon", "alga nori"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Tonkatsu")) {

                String[] ingredientes = {"cerdo", "harina de trigo", "pan"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Tempura")) {

                String[] ingredientes = {"camarones", "harina de trigo"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Yakitori")) {

                String[] ingredientes = {"pollo", "cebolla", "arroz"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Okonomiyaki")) {

                String[] ingredientes = {"harina de trigo", "repollo"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Oyakodon")) {

                String[] ingredientes = {"pollo", "cebolla", "arroz"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Katsudon")) {

                String[] ingredientes = {"cerdo", "cebolla", "arroz"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Nikujaga")) {

                String[] ingredientes = {"cerdo", "papa", "cebolla", "zanahoria"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Karaage")) {

                String[] ingredientes = {"pollo", "harina de trigo"};

                for (String ingrediente : ingredientes) {

                    int resultadoIngrediente = metem.agregarProdEnt(ingrediente, cantidadIngrediente, fecha);
                    int cantEnt = metem.actualizarCantidadIvEnt(ingrediente);
                    int cantSal = metem.actualizarCantidadIvSal(ingrediente);
                    int cantidadTotal = cantEnt + cantidadIngrediente;
                    int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, ingrediente);

                }

            } else if (producto.equals("Sake")) {

                int resultadoIngrediente = metem.agregarProdEnt("Sake", cantidadIngrediente, fecha);
                int cantEnt = metem.actualizarCantidadIvEnt("Sake");
                int cantSal = metem.actualizarCantidadIvSal("Sake");
                int cantidadTotal = cantEnt + cantidadIngrediente;
                int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Sake");

            } else if (producto.equals("Amazake")) {

                int resultadoIngrediente = metem.agregarProdEnt("Amazake", cantidadIngrediente, fecha);
                int cantEnt = metem.actualizarCantidadIvEnt("Amazake");
                int cantSal = metem.actualizarCantidadIvSal("Amazake");
                int cantidadTotal = cantEnt + cantidadIngrediente;
                int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Amazake");

            } else if (producto.equals("Ramune")) {

                int resultadoIngrediente = metem.agregarProdEnt("Ramune", cantidadIngrediente, fecha);
                int cantEnt = metem.actualizarCantidadIvEnt("Ramune");
                int cantSal = metem.actualizarCantidadIvSal("Ramune");
                int cantidadTotal = cantEnt + cantidadIngrediente;
                int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Ramune");

            } else if (producto.equals("Agua")) {

                int resultadoIngrediente = metem.agregarProdEnt("Ramune", cantidadIngrediente, fecha);
                int cantEnt = metem.actualizarCantidadIvEnt("Ramune");
                int cantSal = metem.actualizarCantidadIvSal("Ramune");
                int cantidadTotal = cantEnt + cantidadIngrediente;
                int resultadoActualizarIvTemporal = metem.actualizarIvTemporal(cantidadTotal, cantSal, fecha, "Ramune");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el producto correctamente");
            }
        }
    }

    public void guardarCategoria(String categoria, JComboBox boxproductos) {

        if (categoria.equals("Platos")) {
            boxproductos.removeAllItems();
            boxproductos.addItem("Seleccione el plato...");
            boxproductos.addItem("Sushi");
            boxproductos.addItem("Onigiri");
            boxproductos.addItem("Tonkatsu");
            boxproductos.addItem("Yakitori");
            boxproductos.addItem("Okonomiyaki");
            boxproductos.addItem("Oyakodon");
            boxproductos.addItem("Nikujaga");
            boxproductos.addItem("Karaage");
            boxproductos.addItem("Katsudon");
            boxproductos.addItem("Tempura");
        }
        if (categoria.equals("Bebidas")) {
            boxproductos.removeAllItems();
            boxproductos.addItem("Seleccione la bebida");
            boxproductos.addItem("Sake");
            boxproductos.addItem("Amazake");
            boxproductos.addItem("Ramune");
            boxproductos.addItem("Agua");
        }

        if (categoria.equals("Elija una categoria...")) {
            boxproductos.removeAllItems();
            boxproductos.addItem("Elija el producto...");
            JOptionPane.showMessageDialog(null, "Por favor, seleccione la categoria.");
        }

    }

    // Método para consultar el inventario de productos
    public void consultarInventario(String producto, String metodo, String fecha, JTable tablainv, DefaultTableModel modelo) {

        modelo = (DefaultTableModel) tablainv.getModel();

        if (producto.equals("Elija el producto...") || metodo.equals("Elija el metodo de consulta...") || fecha.equals("Seleccione la fecha...")) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos antes de guardar.");
        } else {

            if (metodo.equals("Ultimas fechas")) {
                modelo.setRowCount(0);
                UtilidadesTablas.tablaNormal(tablainv);
                listarUltimasEnt(tablainv, producto);
                listarUltimasSal(tablainv, producto);
            } else if (metodo.equals("Seleccionar fecha")) {
                modelo.setRowCount(0);
                UtilidadesTablas.tablaNormal(tablainv);
                listarProdEnt(tablainv, producto, fecha);
                listarProdSal(tablainv, producto, fecha);
            }
        }

    }

    public void metodoConsulta(String producto, String metodo, JComboBox seleccionarFecha) {

        if ("Elija el producto...".equals(producto) || "Elija el metodo de consulta...".equals(metodo)) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos antes de guardar.");
            seleccionarFecha.removeAllItems();
            seleccionarFecha.addItem("Seleccione la fecha...");

        } else {

            MetodosEmpleados metodos = new MetodosEmpleados();
            List<String> fechasEnt = metodos.seleccionarFechasEnt(producto);
            List<String> fechasSal = metodos.seleccionarFechasSal(producto);
            Set<String> fechasUnicas = new HashSet<>(); // Conjunto para almacenar fechas únicas

            if ("Seleccionar fecha".equals(metodo)) {
                seleccionarFecha.removeAllItems();
                for (String fecha : fechasEnt) {
                    fechasUnicas.add(fecha);
                }

                // Agregar fechas de salida al conjunto
                for (String fecha : fechasSal) {
                    fechasUnicas.add(fecha);
                }

                for (String fecha : fechasUnicas) {
                    seleccionarFecha.addItem(fecha);
                }
            }

            if ("Elija el producto...".equals(producto) || "Elija el metdodo de consulta...".equals(metodo)) {
                seleccionarFecha.removeAllItems();
                seleccionarFecha.addItem("Seleccione la fecha...");

            }

            if ("Ultimas fechas".equals(metodo)) {
                seleccionarFecha.removeAllItems();
                seleccionarFecha.addItem("Ultimas fechas");
            }

        }
    }

    public void realizarInformeInventario(JTable tablainv, String boxProductos, String boxmetodo, String seleccionarFecha) {

        if (boxProductos.equals("Elija el producto...") || boxmetodo.equals("Elija el metodo de consulta...") || seleccionarFecha.equals("Seleccione la fecha...")) {
            JOptionPane.showMessageDialog(null, "Debe consultar el inventario de productos antes de realizar un informe.", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            ExportarExcel obj;
            obj = new ExportarExcel();
            try {
                obj.exportarExcel(tablainv);
            } catch (IOException ex) {
                Logger.getLogger(ConsultarInventarioo.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Se realiza el informe de inventario.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void ExportRegistFact(JTable tabla) {
        ExportarExcel obj;
        if (tabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Por favor carge los registros antes de exportar");
        } else

             try {
            obj = new ExportarExcel();
            obj.exportarExcel(tabla);
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    //Ilusion de animacion.
   private void changeColor(javax.swing.JLabel label, java.awt.Color startColor, java.awt.Color endColor) {
    javax.swing.Timer timer = new javax.swing.Timer(100, new ActionListener() {
        float t = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            t += 0.1f;
            if (t >= 1) {
                ((javax.swing.Timer)e.getSource()).stop();
            }
            int r = (int)(startColor.getRed() + t * (endColor.getRed() - startColor.getRed()));
            int g = (int)(startColor.getGreen() + t * (endColor.getGreen() - startColor.getGreen()));
            int b = (int)(startColor.getBlue() + t * (endColor.getBlue() - startColor.getBlue()));
            label.setForeground(new java.awt.Color(r, g, b));
        }
    });
    timer.start();
   }
}
