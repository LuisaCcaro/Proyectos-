
package Vista;
import Controlador.Controlador;
import Modelo.CerrarCaja;
import Modelo.MetodosEmpleados;
import Modelo.UsuarioActual;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import Modelo.UtilidadesTablas;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Menu_Admin extends javax.swing.JFrame {
    
    private Inicio inicio;
    
    public Menu_Admin(Inicio inicio) {
        this.inicio = inicio;
        initComponents();
    
        Borrar_Cliente borrarCliente = new Borrar_Cliente();
        Actualizar_Cliente actC = new Actualizar_Cliente();
        CrearMesero cm = new CrearMesero();
    }
    
    
    
    public void mostrarpanel(JPanel p){
        p.setSize(1557, 920);
        p.setLocation(0, 0);
        //913, 616] 1557, 908. 1668, 920 
        SwingUtilities.invokeLater(() -> {
        M_content.removeAll();
        M_content.add(p, BorderLayout.CENTER);
        M_content.revalidate();
        M_content.repaint();
        });
    }
 
  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        Menu = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnIngresar_Pedido = new javax.swing.JButton();
        btnGenerarFact = new javax.swing.JButton();
        btnRegCliente = new javax.swing.JButton();
        btnAgProducto = new javax.swing.JButton();
        btnConsultarInv = new javax.swing.JButton();
        btnA = new javax.swing.JButton();
        btnCrearCuenta = new javax.swing.JButton();
        btnElegirMesa = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Header = new javax.swing.JPanel();
        nomEmple = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        M_content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1278, 770));

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setMaximumSize(new java.awt.Dimension(1278, 770));
        Background.setMinimumSize(new java.awt.Dimension(1278, 770));
        Background.setPreferredSize(new java.awt.Dimension(1278, 770));

        Menu.setBackground(new java.awt.Color(104, 131, 161));
        Menu.setMaximumSize(new java.awt.Dimension(365, 937));
        Menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(104, 131, 161));

        btnIngresar_Pedido.setBackground(new java.awt.Color(104, 131, 161));
        btnIngresar_Pedido.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnIngresar_Pedido.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar_Pedido.setText("Ingresar pedido");
        btnIngresar_Pedido.setBorder(null);
        btnIngresar_Pedido.setBorderPainted(false);
        btnIngresar_Pedido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar_Pedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresar_PedidoActionPerformed(evt);
            }
        });

        btnGenerarFact.setBackground(new java.awt.Color(104, 131, 161));
        btnGenerarFact.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnGenerarFact.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarFact.setText("Generar factura");
        btnGenerarFact.setBorder(null);
        btnGenerarFact.setBorderPainted(false);
        btnGenerarFact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerarFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarFactActionPerformed(evt);
            }
        });

        btnRegCliente.setBackground(new java.awt.Color(104, 131, 161));
        btnRegCliente.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnRegCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnRegCliente.setText("Registrar cliente");
        btnRegCliente.setBorder(null);
        btnRegCliente.setBorderPainted(false);
        btnRegCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegClienteActionPerformed(evt);
            }
        });

        btnAgProducto.setBackground(new java.awt.Color(104, 131, 161));
        btnAgProducto.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnAgProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgProducto.setText("Agregar producto");
        btnAgProducto.setBorder(null);
        btnAgProducto.setBorderPainted(false);
        btnAgProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgProductoActionPerformed(evt);
            }
        });

        btnConsultarInv.setBackground(new java.awt.Color(104, 131, 161));
        btnConsultarInv.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnConsultarInv.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarInv.setText("Consultar inventario");
        btnConsultarInv.setBorder(null);
        btnConsultarInv.setBorderPainted(false);
        btnConsultarInv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarInvActionPerformed(evt);
            }
        });

        btnA.setBackground(new java.awt.Color(255, 142, 164));
        btnA.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnA.setForeground(new java.awt.Color(255, 255, 255));
        btnA.setText("Cerrar sesión");
        btnA.setBorder(null);
        btnA.setBorderPainted(false);
        btnA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAActionPerformed(evt);
            }
        });

        btnCrearCuenta.setBackground(new java.awt.Color(104, 131, 161));
        btnCrearCuenta.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnCrearCuenta.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearCuenta.setText("Crear nueva cuenta");
        btnCrearCuenta.setBorder(null);
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrearCuenta.setMaximumSize(new java.awt.Dimension(134, 22));
        btnCrearCuenta.setMinimumSize(new java.awt.Dimension(134, 22));
        btnCrearCuenta.setPreferredSize(new java.awt.Dimension(134, 22));
        btnCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearCuentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGenerarFact, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(btnIngresar_Pedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(btnConsultarInv, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(btnCrearCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnRegCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIngresar_Pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerarFact, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConsultarInv, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnA, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        Menu.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 365, 470));

        btnElegirMesa.setBackground(new java.awt.Color(104, 131, 161));
        btnElegirMesa.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnElegirMesa.setForeground(new java.awt.Color(255, 255, 255));
        btnElegirMesa.setText("Elegir mesa");
        btnElegirMesa.setBorder(null);
        btnElegirMesa.setBorderPainted(false);
        btnElegirMesa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnElegirMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElegirMesaActionPerformed(evt);
            }
        });
        Menu.add(btnElegirMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 365, 52));

        jPanel3.setBackground(new java.awt.Color(104, 131, 161));

        jLabel3.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Administrador");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/Logo_circular_floral_negro__1_-removebg-preview (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(38, 38, 38))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        Menu.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, 220));

        Header.setBackground(new java.awt.Color(167, 193, 225));

        nomEmple.setBackground(new java.awt.Color(255, 255, 255));
        nomEmple.setFont(new java.awt.Font("Roboto Black", 0, 48)); // NOI18N
        nomEmple.setForeground(new java.awt.Color(255, 255, 255));
        nomEmple.setText("Hola, nombre");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("¡En estos momentos estás en ADMINISTRATIVO!");

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomEmple, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(nomEmple, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        M_content.setBackground(new java.awt.Color(255, 255, 255));
        M_content.setMaximumSize(new java.awt.Dimension(913, 616));
        M_content.setMinimumSize(new java.awt.Dimension(913, 616));
        M_content.setPreferredSize(new java.awt.Dimension(913, 616));

        javax.swing.GroupLayout M_contentLayout = new javax.swing.GroupLayout(M_content);
        M_content.setLayout(M_contentLayout);
        M_contentLayout.setHorizontalGroup(
            M_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 913, Short.MAX_VALUE)
        );
        M_contentLayout.setVerticalGroup(
            M_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BackgroundLayout = new javax.swing.GroupLayout(Background);
        Background.setLayout(BackgroundLayout);
        BackgroundLayout.setHorizontalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(M_content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        BackgroundLayout.setVerticalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(M_content, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresar_PedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresar_PedidoActionPerformed
         
        try {
            MetodosEmpleados metodos = new MetodosEmpleados();
            IngresarPedido p2 = new IngresarPedido();
            Controlador c = new Controlador();
            
            mostrarpanel(p2);
            UtilidadesTablas.tablaNormal(p2.tablePedido);
            c.datosPedidos(p2.tablePedido);
            
            metodos.registrarAccionEmpleado("ingresar pedido");
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnIngresar_PedidoActionPerformed

    private void btnAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAActionPerformed
        
        MetodosEmpleados metem = new MetodosEmpleados();
        
        int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
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

                // Captura de excepciones adicionales
                try {
                    metem.obtenerIdEmpleado(Usuario, hora, fechita);

                    // Insertar cierre de caja
                    metem.insertarCierreCaja(Usuario, saldoInicial, entrada, caja, valorActual);

                    CerrarCaja.cerrarSesion();
                    this.setVisible(false);
                    inicio.setVisible(false);
                    
                    inicio.InputUser.setText("");
                    inicio.InputPsswordUser.setText("");
                    
                    inicio.setVisible(true);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error al insertar el cierre de caja: " + ex.getMessage());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al obtener la fecha y hora: " + ex.getMessage());
            }
        }

    }//GEN-LAST:event_btnAActionPerformed

    private void btnElegirMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElegirMesaActionPerformed
       
        try {
            MetodosEmpleados metodos = new MetodosEmpleados();
            ingresarMesaa p1 = new ingresarMesaa();
            Controlador c = new Controlador();
            
            mostrarpanel(p1);
            UtilidadesTablas.tablaNormal(p1.tblMesa);
            c.listarAGGM(p1.tblMesa);
            
            metodos.registrarAccionEmpleado("elegir mesa");
            
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnElegirMesaActionPerformed

    private void btnRegClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegClienteActionPerformed
        try {
            MetodosEmpleados metodos = new MetodosEmpleados();
            RegistrarCliente p3 = new RegistrarCliente();
            Controlador c = new Controlador();
            
            p3.setAdm(this);
            mostrarpanel(p3);
            UtilidadesTablas.tablaNormal(p3.tablacliente);
            c.listar(p3.tablacliente);
            
            metodos.registrarAccionEmpleado("registrar cliente");
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegClienteActionPerformed

    private void btnGenerarFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarFactActionPerformed
        try {
            MetodosEmpleados metodos = new MetodosEmpleados();
            GenerarFactura p4 = new GenerarFactura();
            mostrarpanel(p4);
            
            metodos.registrarAccionEmpleado("generar factura");
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGenerarFactActionPerformed

    private void btnAgProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgProductoActionPerformed
        
        try {
            MetodosEmpleados metodos = new MetodosEmpleados();
            AgregarProductoo p5 = new AgregarProductoo();
            mostrarpanel(p5);
            
            metodos.registrarAccionEmpleado("agregar producto");
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgProductoActionPerformed

    private void btnConsultarInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarInvActionPerformed
       
        try {
            MetodosEmpleados metodos = new MetodosEmpleados();
            ConsultarInventarioo p8 = new ConsultarInventarioo();
            p8.setAdm(this);
            mostrarpanel(p8);
            
            metodos.registrarAccionEmpleado("consultar inventario de productos");
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnConsultarInvActionPerformed

    private void btnCrearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearCuentaActionPerformed
        
        try {
            MetodosEmpleados metodos = new MetodosEmpleados();
            CrearCuenta p7 = new CrearCuenta();
            p7.setAdm(this);
            mostrarpanel(p7);
            
            metodos.registrarAccionEmpleado("crear cuenta");
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCrearCuentaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JPanel Header;
    public javax.swing.JPanel M_content;
    private javax.swing.JPanel Menu;
    public javax.swing.JButton btnA;
    private javax.swing.JButton btnAgProducto;
    private javax.swing.JButton btnConsultarInv;
    private javax.swing.JButton btnCrearCuenta;
    private javax.swing.JButton btnElegirMesa;
    private javax.swing.JButton btnGenerarFact;
    public javax.swing.JButton btnIngresar_Pedido;
    private javax.swing.JButton btnRegCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel nomEmple;
    // End of variables declaration//GEN-END:variables
}
