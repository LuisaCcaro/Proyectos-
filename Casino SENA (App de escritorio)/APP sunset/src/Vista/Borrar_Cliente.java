package Vista;

import Controlador.Controlador;
import Modelo.Clientes;
import Modelo.MetodosEmpleados;
import Modelo.UtilidadesTablas;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Borrar_Cliente extends javax.swing.JPanel {
    
    private Menu_Cajero caj;
    private Menu_Admin adm;
    
    // Crear una instancia de RegistrarCliente
    private RegistrarCliente rc = new RegistrarCliente();

    public Borrar_Cliente() {
        initComponents();
    }

    // Constructor con parámetro de la clase
    public Borrar_Cliente(Menu_Cajero caj) {
        initComponents();
    }
    
    public Borrar_Cliente(Menu_Admin adm) {
        initComponents();
    }
    
    // Método para establecer la referencia a Menu_Cajero
    public void setCaj(Menu_Cajero caj) {
        this.caj = caj;
    }

    public void setAdm(Menu_Admin adm) {
        this.adm = adm;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BorrarC = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Inputid = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaclienteBorrar = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBorrarCl = new javax.swing.JButton();
        btnBuscarCl1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        volver = new javax.swing.JToggleButton();

        BorrarC.setBackground(new java.awt.Color(255, 255, 255));
        BorrarC.setMaximumSize(new java.awt.Dimension(913, 616));
        BorrarC.setMinimumSize(new java.awt.Dimension(913, 616));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 3, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 102));
        jLabel2.setText("BORRAR CLIENTE");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setText("ID");

        Inputid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputidActionPerformed(evt);
            }
        });

        tablaclienteBorrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "APELLIDO", "C.C", "DIRECCIÓN", "TELÉFONO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaclienteBorrar);
        if (tablaclienteBorrar.getColumnModel().getColumnCount() > 0) {
            tablaclienteBorrar.getColumnModel().getColumn(0).setResizable(false);
            tablaclienteBorrar.getColumnModel().getColumn(1).setResizable(false);
            tablaclienteBorrar.getColumnModel().getColumn(2).setResizable(false);
            tablaclienteBorrar.getColumnModel().getColumn(3).setResizable(false);
            tablaclienteBorrar.getColumnModel().getColumn(4).setResizable(false);
            tablaclienteBorrar.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Roboto Black", 3, 14)); // NOI18N
        jLabel1.setText("INTRODUZCA EL ID DEL CLIENTE QUE DESEA BORRAR. ");

        jLabel3.setFont(new java.awt.Font("Roboto", 2, 14)); // NOI18N
        jLabel3.setText("RECUERDE QUE LOS CAMBIOS SON PERMANENTES.");

        btnBorrarCl.setBackground(new java.awt.Color(255, 102, 102));
        btnBorrarCl.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnBorrarCl.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarCl.setText("Borrar");
        btnBorrarCl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarClActionPerformed(evt);
            }
        });

        btnBuscarCl1.setBackground(new java.awt.Color(104, 131, 161));
        btnBuscarCl1.setFont(new java.awt.Font("Roboto", 2, 12)); // NOI18N
        btnBuscarCl1.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCl1.setText("Buscar");
        btnBuscarCl1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarCl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCl1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(Inputid, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnBuscarCl1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(btnBorrarCl))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Inputid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnBuscarCl1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(btnBorrarCl))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/samurai-removebg-preview.png"))); // NOI18N

        volver.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        volver.setForeground(new java.awt.Color(255, 102, 102));
        volver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/volvwe__1_-removebg-preview.png"))); // NOI18N
        volver.setText("VOLVER");
        volver.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        volver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BorrarCLayout = new javax.swing.GroupLayout(BorrarC);
        BorrarC.setLayout(BorrarCLayout);
        BorrarCLayout.setHorizontalGroup(
            BorrarCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BorrarCLayout.createSequentialGroup()
                .addGroup(BorrarCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BorrarCLayout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addGroup(BorrarCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(BorrarCLayout.createSequentialGroup()
                                .addGroup(BorrarCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(267, 267, 267))))
                    .addGroup(BorrarCLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(volver)
                        .addGap(734, 734, 734)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(281, 281, 281))
        );
        BorrarCLayout.setVerticalGroup(
            BorrarCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BorrarCLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(348, 348, 348))
            .addGroup(BorrarCLayout.createSequentialGroup()
                .addGroup(BorrarCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BorrarCLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(volver))
                    .addGroup(BorrarCLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BorrarC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BorrarC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarCl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCl1ActionPerformed
    
        MetodosEmpleados metodos = new MetodosEmpleados();
        String id = Inputid.getText();
        Clientes cliente = metodos.unicoCliente(id);
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor,asegurese de llenar el campo antes de buscar","ERROR" ,JOptionPane.ERROR_MESSAGE);

        }
        if(cliente != null) {
             Controlador enlista = new Controlador();
             UtilidadesTablas.tablaNormal(tablaclienteBorrar);
             enlista.listarborradoCliente(tablaclienteBorrar, cliente);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado","ERROR" ,JOptionPane.ERROR_MESSAGE);
            Inputid.setText(""); 
        }

    }//GEN-LAST:event_btnBuscarCl1ActionPerformed

    private void InputidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputidActionPerformed

    private void btnBorrarClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarClActionPerformed
        
        //Obtenemos el modelo
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaclienteBorrar.getModel();
        
        MetodosEmpleados metodos = new MetodosEmpleados();
        
        String id = Inputid.getText();
        metodos.eliminarCliente(id);
        Inputid.setText(""); 
        
        //Limpieza de tabla
        modeloTabla.setRowCount(0);
    }//GEN-LAST:event_btnBorrarClActionPerformed

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
        Controlador c = new Controlador();
        RegistrarCliente p = new RegistrarCliente();
        UtilidadesTablas.tablaNormal(rc.tablacliente);
        c.listar(rc.tablacliente);
        if(caj != null) {

            // Establecer la referencia a la clase Menu_Cajero en el panel RegistrarCliente
            rc.setCaj(caj);

            // Mostrar el panel RegistrarCliente en el contenedor principal
            caj.mostrarC(rc);
        } else {
            
            rc.setAdm(adm);
            adm.mostrarpanel(rc);
        }
        
    }//GEN-LAST:event_volverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BorrarC;
    public javax.swing.JTextField Inputid;
    public javax.swing.JButton btnBorrarCl;
    public javax.swing.JButton btnBuscarCl1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JTable tablaclienteBorrar;
    private javax.swing.JToggleButton volver;
    // End of variables declaration//GEN-END:variables
}
