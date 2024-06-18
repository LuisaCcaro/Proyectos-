package controlador;

import modelo.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import vista.SistemaCalificaciones;
import modelo.Alumnos;

public class Controlador implements ActionListener {

    private SistemaCalificaciones vista;
    private MetodosAlumnos metodos;
    Conexion con = new Conexion();
    Connection cn;

    public Controlador(SistemaCalificaciones vista) {
        this.vista = vista;
        this.metodos = new MetodosAlumnos();
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this); // Asocia el método "insertar" al botón "guardado"
        this.vista.btnCargar.addActionListener(this);
        this.vista.btnProcesar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            insertar();
        }
        else if (e.getSource() == vista.btnConsultar) {
            consultarbtnG();
        }
        else if (e.getSource() == vista.btnEliminar) {
            eliminarbtnE();
        }
        
        else if(e.getSource() == vista.btnCargar) {

            try {
                Alumnos[] estudiantes = metodos.loadData();
                metodos.mostrarDatos(vista.txtACargar_Datos, estudiantes);
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(e.getSource() == vista.btnProcesar) {
            
            MetodosAlumnos mta = new MetodosAlumnos();
            
            try {
                metodos.procesarDatosYMostrar(vista.txtAMostrar_Datos);
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void insertar() {
        String nombre = vista.inputNombre.getText();
        double nota = Double.parseDouble(vista.inputNota.getText()); // Convierte la entrada de "nota" a un valor double
        String genero = vista.inputGenero.getText();
        String materia = vista.inputMateria.getText();

        Alumnos alumno = new Alumnos(nombre, nota, genero, materia);
        if(nombre.isEmpty() || genero.isEmpty() || materia.isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor,llene todos los campos");

        }
        if (metodos.insertarAlumno(alumno)) {
            JOptionPane.showMessageDialog(null, "El alumno se ha registrado correctamente");
            limpieza();
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error, compruebe que todo esté en orden.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void consultarbtnG(){

        String nombre = vista.inputNom.getText().trim();
        String materia = vista.inputMat.getText().trim();
        MetodosAlumnos c = new MetodosAlumnos();


        if (nombre.isEmpty() && materia.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor,llene algun campo");
        } else {
            Tablas.tablacentrada(vista.TablaE);
            DefaultTableModel model = (DefaultTableModel) vista.TablaE.getModel();

  
            model.setRowCount(0);


            if (!nombre.isEmpty()) {
                List<Alumnos> estudiantes = c.consultarEstudiantes(nombre);
                for (Alumnos estudiante : estudiantes) {
                    Object[] row = new Object[]{estudiante.getNombre(), estudiante.getNota(), estudiante.getGenero(), estudiante.getMateria()};
                    model.insertRow(0, row); 
                }
            }
            

 
            if (!materia.isEmpty()) {
                List<Alumnos> estudiantess = c.consultarPorMateria(materia);
                for (Alumnos estudiant : estudiantess) {
                    Object[] row = new Object[]{estudiant.getNombre(), estudiant.getNota(), estudiant.getGenero(), estudiant.getMateria()};
                    model.insertRow(0, row); 
                }
            }
            
            vista.inputNom.setText("");
            vista.inputMat.setText("");
        }

    }
    
    public void eliminarbtnE(){
            String nombre = vista.inputNom.getText();
            String materia = vista.inputMat.getText();
            DefaultTableModel modelotabla = (DefaultTableModel) vista.TablaE.getModel();
            if (nombre.isEmpty() && materia.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, llene algún campo");
            } else {
                if (!nombre.isEmpty()) {
                    metodos.eliminarPorNombre(nombre);
                }
                if (!materia.isEmpty()) {
                    metodos.eliminarPorMeterias(materia);
                }
                
            }
            vista.inputNom.setText("");
            vista.inputMat.setText("");
            modelotabla.setRowCount(0);

    
    }
    private void limpieza(){
        vista.inputNombre.setText("");
        vista.inputNota.setText("");
        vista.inputGenero.setText("");
        vista.inputMateria.setText("");
    }
}


